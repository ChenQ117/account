package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.ArrayList;
import java.util.List;

import Activity.EventDetail;
import Activity.PersonDetail;
import Database.Connection;
import Database.ConnectionDao;
import Database.Event;
import Database.EventDao;
import Database.Person;
import Database.PersonDao;

/**
 * 适配器，提供给主活动recycleView页面使用
 * 每一条的布局为list_layout
 * 该布局包括：活动名称，金额，是否付清（点击活动名称开启下一活动）
 * 开启活动：PersonDetail.class
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    List<Event> allEvent;
    Context mContext;
    List<Integer> person_idlist;
    List<Person> mPeople;
    List<Connection> mConnections;
    ConnectionDao mConnectionDao;
    PersonDao mPersonDao;
    EventDao mEventDao;

    public EventAdapter(List<Event> allEvent,Context context,ConnectionDao connectionDao,PersonDao personDao) {
        mConnectionDao = connectionDao;
        mPersonDao = personDao;
        this.allEvent = allEvent;
        mContext = context;
    }

    public void setAllEvent(List<Event> allEvent) {
        this.allEvent = allEvent;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中要把它加载这个view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemview=layoutInflater.inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if(!allEvent.isEmpty()){
            final Event event=allEvent.get(position);
            holder.eventName.setText(event.getActivity());
            holder.allMoney.setText(""+event.getAmount());
            holder.eventName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PersonDetail.class);
                    intent.putExtra("event_id",event.getId());
                    mContext.startActivity(intent);
                }
            });
            if(event.isEmpty()){
                holder.mCheckBox.setChecked(true);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    person_idlist = mConnectionDao.findPersonIdByEventId(event.getId());
                    mPeople = mPersonDao.findPersonById(person_idlist);
                    holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                event.setEmpty(true);
                                for (int i=0;i<mPeople.size();i++){
                                    Person person = mPeople.get(i);
                                    Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event.getId(),person.getId());
                                    person.setMoney(person.getMoney()-connection.getSinglemoney());
                                    connection.setPay(true);
                                    mPersonDao.updatePerson(person);
                                    mConnectionDao.updateConnection(connection);
                                }
                            }else {
                                event.setEmpty(false);
                                for (int i=0;i<mPeople.size();i++) {
                                    Person person = mPeople.get(i);
                                    Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event.getId(), person.getId());
                                    person.setMoney(person.getMoney() + connection.getSinglemoney());
                                    connection.setPay(false);
                                    mPersonDao.updatePerson(person);
                                    mConnectionDao.updateConnection(connection);
                                }
                            }
                        }
                    });
                }
            }).start();

        }
    }


    @Override
    public int getItemCount() {
        return allEvent.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventName;
        TextView allMoney;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName =itemView.findViewById(R.id.name);
            allMoney = itemView.findViewById(R.id.allMoney);
            mCheckBox = itemView.findViewById(R.id.isPay);
        }
    }

}
