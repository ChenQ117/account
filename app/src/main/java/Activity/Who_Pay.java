package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.account.R;

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

public class Who_Pay extends AppCompatActivity {
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
    CheckBox cb;

    boolean flag_btnext = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_pay_layout);
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

        nextButton = findViewById(R.id.button2);
        mRecyclerView = findViewById(R.id.s2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        event_id = intent.getIntExtra("event_id",0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //获得参与这项活动的人的id
                List<Integer> personList = mConnectionDao.findPersonIdByEventId(event_id);

                //获得参与这项活动的人的所有信息
                person = mPersonDao.findPersonById(personList);
                mPayAdapter = new PayAdapter(mCheckBoxEditTextMap,person);
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
                    Set<CheckBox> checkBoxes = mCheckBoxEditTextMap.keySet();
                    int flag = 0;//用于判断金额是否填写不合理
                    for(final CheckBox checkBox: checkBoxes){
                        if(checkBox.isChecked()){

                            final String moneys = mCheckBoxEditTextMap.get(checkBox).getText().toString().trim();
                            if("".equals(moneys)){
                                flag = 1;
                                Toast.makeText(v.getContext(),"金额填写错误",Toast.LENGTH_SHORT).show();
                            }else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int money = Integer.parseInt(moneys);
                                        Person person = mPersonDao.findPersonMoney(checkBox.getText().toString().trim());
                                        Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event_id,person.getId());
                                        connection.setSinglemoney(money);
                                        mConnectionDao.updateConnection(connection);
                                        person.setMoney(person.getMoney()+money);
                                        mPersonDao.updatePerson(person);
                                    }
                                }).start();
                            }
                        }
                    }
                    if(flag == 0){
                        Toast.makeText(Who_Pay.this,"添加成功",Toast.LENGTH_SHORT).show();
                        ActivityCollector.finishAll();
                    }
                }
            });
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
