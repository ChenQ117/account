package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.account.R;

import java.util.List;

import Adapter.PersonInEventAdapter;
import Database.Connection;
import Database.ConnectionDao;
import Database.ConnectionDatabase;
import Database.ConnectionViewModel;
import Database.Event;
import Database.EventDao;
import Database.EventDatabase;
import Database.EventViewModel;
import Database.Person;
import Database.PersonDao;
import Database.PersonDatabase;
import Database.PersonViewModel;

/**
 * 显示参与某一活动的所有人的信息
 * 页面布局：detail_layout
 * 适配器：PersonInEventAdapter
 */
public class PersonDetail extends AppCompatActivity {
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
    PersonInEventAdapter mPersonInEventAdapter;

    TextView tv_eventName;//活动名称
    Button bt_finish;//完成
    Button bt_delete;//删除

    List<Integer> person_idlist;
    List<Person> mPeople;

    int event_id;
    boolean flag_btfinish = false;
    boolean flag_btdelete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        Intent intent =getIntent();
        event_id = intent.getIntExtra("event_id",0);


        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDatabase = Room.databaseBuilder(this, EventDatabase.class,"event_database.db").build();
        mEventDao = mEventDatabase.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDatabase = Room.databaseBuilder(this, PersonDatabase.class,"person_database.db").build();
        mPersonDao = mPersonDatabase.getPersonDao();
        mConnectionViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        mConnectionDatabase = Room.databaseBuilder(this, ConnectionDatabase.class,"connection_database.db").build();
        mConnectionDao = mConnectionDatabase.getConnectionDao();

        mRecyclerView = findViewById(R.id.s3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_eventName = findViewById(R.id.personName);
        bt_finish = findViewById(R.id.button_end_detail);
        if (!flag_btfinish){
            bt_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag_btfinish = true;
                    PersonDetail.this.finish();
                }
            });

        }

        bt_delete = findViewById(R.id.button_delete);
        if(!flag_btdelete){
            bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag_btdelete = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Event event = mEventDao.findEventByEventId(event_id);
                            mEventDao.deleteEvent(event);
                            List<Connection> connections = mConnectionDao.findConnectionByEventId(event_id);
                            for (int i=0;i<connections.size();i++){
                                Connection temp_connection = connections.get(i);
                                if (!temp_connection.isPay()){
                                    temp_connection.setPay(true);
                                    Person person_temp = mPersonDao.findSinglePersonById(temp_connection.getPersonId());
                                    if (person_temp !=null){
                                        person_temp.setMoney(person_temp.getMoney() - temp_connection.getSinglemoney());
                                        if(person_temp.getMoney()==0) {
                                            mPersonDao.deletePerson(person_temp);
                                        }
                                    }
                                }
                            }
                            mConnectionDao.deleteConnection(connections);
                        }
                    }).start();
                    PersonDetail.this.finish();
                }
            });
        }

        //设置页面中的活动名称，通过数据库获取参与该项活动的所有人对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Event event = mEventDao.findEventByEventId(event_id);

                if (event!=null){
                    //设置页面中的活动名称
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_eventName.setText(event.getActivity());
                        }
                    });
                }


                //通过数据库获取参与该项活动的所有人对象
                person_idlist = mConnectionDao.findPersonIdByEventId(event_id);
                mPeople = mPersonDao.findPersonById(person_idlist);

                mPersonInEventAdapter = new PersonInEventAdapter(PersonDetail.this,mConnectionDao,mPersonDao,mEventDao
                ,person_idlist,mPeople,event_id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mPersonInEventAdapter);
                    }
                });

            }
        }).start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //通过数据库获取参与该项活动的所有人对象
                person_idlist = mConnectionDao.findPersonIdByEventId(event_id);
                mPeople = mPersonDao.findPersonById(person_idlist);

                mPersonInEventAdapter = new PersonInEventAdapter(PersonDetail.this,mConnectionDao,mPersonDao,mEventDao
                        ,person_idlist,mPeople,event_id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mPersonInEventAdapter);
                    }
                });
            }
        }).start();
    }
}
