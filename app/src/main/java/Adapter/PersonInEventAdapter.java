package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.List;

import Database.Connection;
import Database.ConnectionDao;
import Database.EventDao;
import Database.Person;
import Database.PersonDao;

/**
 * 显示所有参与某一项活动的人
 * 布局：name_layout
 */
public class PersonInEventAdapter extends RecyclerView.Adapter<PersonInEventAdapter.MyViewHoder> {
    List<Integer> person_idlist;
    List<Person> mPeople;
    List<Connection> mConnections;
    ConnectionDao mConnectionDao;
    PersonDao mPersonDao;
    EventDao mEventDao;

    int event_id;

    public PersonInEventAdapter(ConnectionDao connectionDao, PersonDao personDao, EventDao eventDao,
                                List<Integer> person_idlist,List<Person> mPeople,final int event_id) {
        mConnectionDao = connectionDao;
        mPersonDao = personDao;
        mEventDao = eventDao;
        this.event_id = event_id;
        this.person_idlist = person_idlist;
        this.mPeople = mPeople;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.name_layout, null, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, int position) {
        final Person person = mPeople.get(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Connection connection = mConnectionDao.findConnectionByEventIdAndPersonId(event_id,person.getId());
                holder.personName.setText(person.getName());
                holder.singleMoney.setText(""+connection.getSinglemoney());
                if(connection.isPay()){
                    holder.isPay.setChecked(true);
                }
                holder.isPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            connection.setPay(true);
                            person.setMoney(person.getMoney()-connection.getSinglemoney());
                        }else {
                            connection.setPay(false);
                            person.setMoney(person.getMoney()+connection.getSinglemoney());
                        }
                        mConnectionDao.updateConnection(connection);
                        mPersonDao.updatePerson(person);
                    }
                });
            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    static class MyViewHoder extends RecyclerView.ViewHolder{
        TextView personName,singleMoney;
        CheckBox isPay;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.name);
            singleMoney = itemView.findViewById(R.id.single_money);
            isPay = itemView.findViewById(R.id.isPay);
        }
    }
}
