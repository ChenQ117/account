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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.List;

import Activity.EventDetail;
import Database.Connection;
import Database.ConnectionDao;
import Database.EventDao;
import Database.Person;
import Database.PersonDao;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    List<Person> allPerson;
    Context mContext;

    ConnectionDao mConnectionDao;
    PersonDao mPersonDao;
    EventDao mEventDao;

    boolean flag_cardView = false;

    public PersonAdapter(ConnectionDao connectionDao ,PersonDao personDao,List<Person> allPerson, Context context) {
        this.allPerson = allPerson;
        mConnectionDao = connectionDao;
        mPersonDao = personDao;
        mContext = context;
    }

    public void setAllPerson(List<Person> allPerson) {
        this.allPerson = allPerson;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中要把它加载这个view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemview=layoutInflater.inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if(!allPerson.isEmpty()){
            final Person person=allPerson.get(position);
            holder.personName.setText(person.getName());
            holder.allMoney.setText(""+person.getMoney());

            //活动跳转
            if (!flag_cardView){
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag_cardView = true;
                        Intent intent = new Intent(mContext, EventDetail.class);
                        intent.putExtra("person_id",person.getId());
                        mContext.startActivity(intent);
                    }
                });
            }

            if(person.isPay()){
                holder.mCheckBox.setChecked(true);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final List<Connection> connectionList = mConnectionDao.findConnectionByPersonId(person.getId());
                    holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        Connection connection;
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                person.setPay(true);
                                for (int i=0;i<connectionList.size();i++){
                                    connection = connectionList.get(i);
                                    connection.setPay(true);
                                }
                            }else {
                                person.setPay(false);
                                for (int i=0;i<connectionList.size();i++){
                                    connection = connectionList.get(i);
                                    connection.setPay(false);
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
    }


    @Override
    public int getItemCount() {
        return allPerson.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView allMoney;
        CheckBox mCheckBox;
        CardView mCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            personName =itemView.findViewById(R.id.name);
            allMoney = itemView.findViewById(R.id.allMoney);
            mCheckBox = itemView.findViewById(R.id.isPay);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }

}
