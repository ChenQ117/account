package Adapter;

import android.app.Activity;
import android.content.Context;
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

import Database.Connection;
import Database.ConnectionDao;
import Database.Event;
import Database.EventDao;
import Database.Person;
import Database.PersonDao;

/**
 * 用于加载某个人所参与的所有活动
 * 每条活动布局为：name_layout
 */
public class EventInPersonAdapter extends RecyclerView.Adapter<EventInPersonAdapter.MyViewHoder> {
    List<Integer> eventIdList;
    List<Event> mEvents;
    List<Connection> mConnections;
    ConnectionDao mConnectionDao;
    PersonDao mPersonDao;
    EventDao mEventDao;
    Activity mActivity;
    int person_id;

    public EventInPersonAdapter(Activity activity,ConnectionDao connectionDao, PersonDao personDao, EventDao eventDao,
                                List<Integer> eventIdList,List<Event> mEvents,final int person_id) {
        mConnectionDao = connectionDao;
        mPersonDao = personDao;
        mEventDao = eventDao;
        mActivity = activity;
        this.person_id = person_id;
        this.eventIdList = eventIdList;
        this.mEvents = mEvents;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.name_layout, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, int position) {
        final Event event = mEvents.get(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Person person = mPersonDao.findSinglePersonById(person_id);
                final Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event.getId(),person_id);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.eventName.setText(event.getActivity());
                        holder.singleMoney.setText(""+connection.getSinglemoney());
                        if(connection.isPay()){
                            holder.isPay.setChecked(true);
                        }
                    }
                });

                holder.isPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if (!connection.isPay()){
                                connection.setPay(true);
                                person.setMoney(person.getMoney()-connection.getSinglemoney());
                            }
                        }else {
                            if (connection.isPay()){
                                connection.setPay(false);
                                person.setMoney(person.getMoney()+connection.getSinglemoney());
                            }
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mConnectionDao.updateConnection(connection);
                                mPersonDao.updatePerson(person);
                            }
                        }).start();
                    }
                });
            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    static class MyViewHoder extends RecyclerView.ViewHolder{
        TextView eventName,singleMoney;
        CheckBox isPay;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.name);
            singleMoney = itemView.findViewById(R.id.single_money);
            isPay = itemView.findViewById(R.id.isPay);
        }
    }
}
