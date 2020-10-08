package com.example.account;

import Activity.Item_Input;
import Activity.Mine_Money;
import Adapter.EventAdapter;
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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * 主活动，页面为多个活动条，点击每个活动条的活动名称可以查看活动详情页面
 * layout：activity_main
 * Adapter:EventAdapter
 */
public class MainActivity extends AppCompatActivity {
    EventViewModel mEventViewModel;
    EventDatabase mEventDatabase;
    EventDao mEventDao;
    PersonViewModel mPersonViewModel;
    PersonDatabase mPersonDatabase;
    PersonDao mPersonDao;
    ConnectionViewModel mConnectionViewModel;
    ConnectionDatabase mConnectionDatabase;
    ConnectionDao mConnectionDao;

    EventAdapter mEventAdapter;
    RecyclerView mRecyclerView;
    List<Event> events;

    boolean flag_fab = false;//用于判断floatbutton的点击事件，只允许点一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDatabase = Room.databaseBuilder(this, EventDatabase.class,"event_database.db").build();
        mEventDao = mEventDatabase.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDatabase = Room.databaseBuilder(this, PersonDatabase.class,"person_database.db").build();
        mPersonDao = mPersonDatabase.getPersonDao();
        mConnectionViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        mConnectionDatabase = Room.databaseBuilder(this, ConnectionDatabase.class,"connection_database.db").build();
        mConnectionDao = mConnectionDatabase.getConnectionDao();

        if (!flag_fab){
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag_fab = true;
                    Intent intent = new Intent(v.getContext(), Item_Input.class);
                    startActivity(intent);
                }
            });
        }

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
        if(mEventAdapter != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //先删除人名为空的活动即无效活动
                    deleteNullEvent();
                    setMyAdapter();
                }
            }).start();
        }
    }

    //剔除无效活动
    public void deleteNullEvent(){
        //先删除人名为空的活动即无效活动，再获得有效活动
        List<Integer> eventIdList = mEventDao.getAllEventId();
        for (int i=0;i<eventIdList.size();i++){
            int temp_eventId = eventIdList.get(i);
            List<Integer> personIdList = mConnectionDao.findPersonIdByEventId(temp_eventId);
            List<Integer> singleMoneyList = mConnectionDao.findSingleMoneyByEventId(temp_eventId);
            int numb = 0;


            //查找与某个活动有关的所有人的单笔金额为0的个数，若全为0，则该活动为无效活动
            for (int j = 0;j<singleMoneyList.size();j++){
                if (singleMoneyList.get(j)==0){
                    numb++;
                }
            }

            //如果与某活动有关所有人已经不存在了，则该活动为无效活动
            if (numb >= singleMoneyList.size()||personIdList.isEmpty()){
                Event event_temp = mEventDao.findEventByEventId(temp_eventId);
                mEventDao.deleteEvent(event_temp);
            }

        }
    }

    //设置适配器
    public void setMyAdapter(){
        //先删除人名为空的活动即无效活动
        deleteNullEvent();

        events = mEventDao.getAllEvent();
        mEventAdapter = new EventAdapter(events,MainActivity.this,mConnectionDao,mPersonDao);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(mEventAdapter);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine:
                Intent intent = new Intent(MainActivity.this, Mine_Money.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
