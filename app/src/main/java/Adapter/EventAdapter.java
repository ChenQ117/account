package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.ArrayList;
import java.util.List;

import Activity.Detail;
import Database.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    List<Event> allEvent=new ArrayList<>();
    Context mContext;

    public EventAdapter(List<Event> allEvent,Context context) {
        this.allEvent = allEvent;
        mContext = context;
    }

    public void setAllEvent(List<Event> allEvent) {
        this.allEvent = allEvent;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中要把它加载这个view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemview=layoutInflater.inflate(R.layout.cell_card,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(!allEvent.isEmpty()){
            final Event event=allEvent.get(position);
            holder.mTextView.setText(event.getActivity());
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Detail.class);
                    intent.putExtra("event_id",event.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return allEvent.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.activityname);
        }
    }

}
