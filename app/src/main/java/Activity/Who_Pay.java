package Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.account.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Adapter.WhoPayAdapter;
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
    EventDao mEventDao;
    PersonViewModel mPersonViewModel;
    PersonDao mPersonDao;
    ConnectionViewModel mConnectionViewModel;
    ConnectionDao mConnectionDao;
    RecyclerView mRecyclerView;
    WhoPayAdapter mWhoPayAdapter;

    Button nextButton;
    EditText ed;
    List<Person> person;//参加活动的人
    Map<CheckBox,EditText> mCheckBoxEditTextMap = new HashMap<>();
    int event_id;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_pay_layout);
        ActivityCollector.addActivity(this);
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDao = mEventViewModel.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDao = mPersonViewModel.getPersonDao();
        mConnectionViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        mConnectionDao = mConnectionViewModel.getConnectionDao();

        nextButton = findViewById(R.id.button2);
        mRecyclerView = findViewById(R.id.s2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        event_id = intent.getIntExtra("event_id",0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //获得参与这项活动的人的id
                List<Integer> personList = mConnectionDao.findConnectionByEventId(event_id);

                //获得参与这项活动的人的所有信息
                person = mPersonDao.findPersonById(personList);
                mWhoPayAdapter = new WhoPayAdapter(mCheckBoxEditTextMap,person);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mWhoPayAdapter);
                    }
                });


            }
        }).start();


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
                                    Connection connection = new Connection(event_id,person.getId());
                                    connection.setSinglemoney(money);
                                    mConnectionViewModel.updateConnection(connection);
                                    person.setMoney(person.getMoney()+money);
                                    mPersonViewModel.updatePerson(person);
                                }
                            }).start();

                        }

                    }
                }
                if(flag == 0){
                    Intent intent1 = new Intent(v.getContext(),Pay_Whom.class);
                    intent1.putExtra("event_id",event_id);
                    startActivity(intent1);
                }
            }
        });

    }


}
