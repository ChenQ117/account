package Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.account.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import Adapter.EventInPersonAdapter;
import Adapter.PersonInEventAdapter;
import Database.ConnectionDao;
import Database.ConnectionDatabase;
import Database.ConnectionViewModel;
import Database.Event;
import Database.EventDao;
import Database.EventDatabase;
import Database.EventViewModel;
import Database.PersonDao;
import Database.PersonDatabase;
import Database.PersonViewModel;

/**
 * 活动详情页面，显示活动总金额与参与人
 */
public class EventDetail extends AppCompatActivity {
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
    EventInPersonAdapter mEventInPersonAdapter;

    List<Integer> eventIdList;
    List<Event> mEvents;

    int person_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        Intent intent =getIntent();
        person_id = intent.getIntExtra("person_id",0);


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
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventIdList = mConnectionDao.findEventIdByPersonId(person_id);
                mEvents = mEventDao.findEventByEventId(eventIdList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEventInPersonAdapter = new EventInPersonAdapter(EventDetail.this,mConnectionDao,mPersonDao,mEventDao,
                                eventIdList,mEvents,person_id);
                        mRecyclerView.setAdapter(mEventInPersonAdapter);
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
                eventIdList = mConnectionDao.findEventIdByPersonId(person_id);
                mEvents = mEventDao.findEventByEventId(eventIdList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEventInPersonAdapter = new EventInPersonAdapter(EventDetail.this,mConnectionDao,mPersonDao,mEventDao,
                                eventIdList,mEvents,person_id);
                        mRecyclerView.setAdapter(mEventInPersonAdapter);
                    }
                });
            }
        }).start();
    }
}
