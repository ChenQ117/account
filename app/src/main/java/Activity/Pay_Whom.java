package Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.account.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Adapter.PayAdapter;
import Database.Connection;
import Database.ConnectionDao;
import Database.ConnectionDatabase;
import Database.ConnectionViewModel;
import Database.EventDao;
import Database.EventDatabase;
import Database.EventViewModel;
import Database.Person;
import Database.PersonDao;
import Database.PersonDatabase;
import Database.PersonViewModel;

public class Pay_Whom extends AppCompatActivity {
    EventViewModel mEventViewModel;
    EventDatabase mEventDatabase;
    EventDao mEventDao;
    PersonViewModel mPersonViewModel;
    PersonDatabase mPersonDatabase;
    PersonDao mPersonDao;
    ConnectionViewModel mConnectionViewModel;
    ConnectionDatabase mConnectionDatabase;
    ConnectionDao mConnectionDao;

    RecyclerView mRecyclerView;
    PayAdapter mPayAdapter;

    Button nextButton;
    EditText ed;
    List<Person> person;//参加活动的人
    Map<CheckBox,EditText> mCheckBoxEditTextMap = new HashMap<>();
    int event_id;
    int allMoney = 0;
    CheckBox cb;

    boolean flag_btnext = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_whom_layout);
        ActivityCollector.addActivity(this);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDatabase = Room.databaseBuilder(this, EventDatabase.class,"event_database.db").build();
        mEventDao = mEventDatabase.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDatabase = Room.databaseBuilder(this, PersonDatabase.class,"person_database.db").build();
        mPersonDao = mPersonDatabase.getPersonDao();
        mConnectionViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        mConnectionDatabase = Room.databaseBuilder(this, ConnectionDatabase.class,"connection_database.db").build();
        mConnectionDao = mConnectionDatabase.getConnectionDao();

        nextButton = findViewById(R.id.button3);
        mRecyclerView = findViewById(R.id.s3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        event_id = intent.getIntExtra("event_id",0);
        allMoney = intent.getIntExtra("allMoney",0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //获得参与这项活动的人的id
                List<Integer> personList = mConnectionDao.findPersonIdByEventId(event_id);

                //获得参与这项活动的人的所有信息
                person = mPersonDao.findPersonById(personList);
                mPayAdapter = new PayAdapter(mCheckBoxEditTextMap,person,allMoney);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mPayAdapter);
                    }
                });


            }
        }).start();


        if (!flag_btnext){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag_btnext = true;
                    Set<CheckBox> checkBoxes = mCheckBoxEditTextMap.keySet();
                    int flag = 0;//用于判断金额是否填写不合理
                    int amoney = 0;
                    for(final CheckBox checkBox: checkBoxes){
                        if(checkBox.isChecked()){
//                            mCheckBoxEditTextMap.get(checkBox).setHint(allMoney-amoney);
                            final String moneys = mCheckBoxEditTextMap.get(checkBox).getText().toString().trim();
                            if("".equals(moneys)){
                                flag = 1;
                                Toast.makeText(v.getContext(),"金额填写错误",Toast.LENGTH_SHORT).show();
                            }else {
                                final int money = Integer.parseInt(moneys);
                                amoney+=money;

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Person person = mPersonDao.findPersonMoney(checkBox.getText().toString().trim());
                                        Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event_id,person.getId());
                                        connection.setSinglemoney(-money);
                                        mConnectionDao.updateConnection(connection);
                                        person.setMoney(person.getMoney()-money);
                                        mPersonDao.updatePerson(person);
                                    }
                                }).start();
                            }
                        }
                    }
                    if (amoney!=allMoney&&flag == 0){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Pay_Whom.this);
                        builder.setTitle("金额填写不合理，是否继续提交？");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Pay_Whom.this,"添加成功",Toast.LENGTH_SHORT).show();
                                ActivityCollector.finishAll();
                            }
                        });
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        Toast.makeText(v.getContext(),"金额填写不合理",Toast.LENGTH_SHORT).show();
                    }else if(flag == 0){

                        Toast.makeText(Pay_Whom.this,"添加成功",Toast.LENGTH_SHORT).show();
                        ActivityCollector.finishAll();
                    }

                }
            });
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
