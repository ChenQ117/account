package com.example.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.Event;
import Database.Person;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    List<Event> allEvent=new ArrayList<>();
    public void  setAllEvent(List<Event> allEvent){
        this.allEvent=allEvent;
    }
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中要把它加载这个view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemview=layoutInflater.inflate(R.layout.cell_card,parent,false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event=allEvent.get(position);
        holder.activityname.setText(event.getActivity());
    }


    @Override
    public int getItemCount() {
        return allEvent.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView activityname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityname=itemView.findViewById(R.id.activityname);




        }
    }

}
