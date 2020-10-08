package Activity;

import android.os.Bundle;

import com.example.account.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import Adapter.PersonAdapter;
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
 * Adapter:PersonAdapter
 */
public class Mine_Money extends AppCompatActivity {
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
    PersonAdapter mPersonAdapter;

    List<Person> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_layout);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDatabase = Room.databaseBuilder(this, EventDatabase.class,"event_database.db").build();
        mEventDao = mEventDatabase.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDatabase = Room.databaseBuilder(this, PersonDatabase.class,"person_database.db").build();
        mPersonDao = mPersonDatabase.getPersonDao();
        mConnectionViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        mConnectionDatabase = Room.databaseBuilder(this, ConnectionDatabase.class,"connection_database.db").build();
        mConnectionDao = mConnectionDatabase.getConnectionDao();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(new Runnable() {
            @Override
            public void run() {
                setMyAdapter();
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(personList!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setMyAdapter();
                }
            }).start();
        }
    }

    //删除钱款为0的人和活动为空的人
    public void deleteNullPerson(){
        List<Person> personList = mPersonDao.findPerson();
        for (int i=0;i<personList.size();i++){
            Person person = personList.get(i);
            List<Integer> eventIdList = mConnectionDao.findEventIdByPersonId(person.getId());
            List<Event> eventByEventId = mEventDao.findEventByEventId(eventIdList);
//            ||(person.getMoney() == 0&&!person.isPay())
            if (eventByEventId.isEmpty()){
                mPersonDao.deletePerson(person);
            }
        }
    }

    //设置适配器
    public void setMyAdapter(){
        deleteNullPerson();
        personList = mPersonDao.findPerson();
        mPersonAdapter = new PersonAdapter(mConnectionDao,mPersonDao,personList,Mine_Money.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(mPersonAdapter);
            }
        });
    }
}
