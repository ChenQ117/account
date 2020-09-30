package com.example.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.Person;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Person> allPerson=new ArrayList<>();
    public void  setAllPerson(List<Person> allPerson){
        this.allPerson=allPerson;
    }
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中要把它加载这个view
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //View itemview=layoutInflater.inflate(R.layout.cell_normal,parent,false);
        View itemview=layoutInflater.inflate(R.layout.cell_normal,parent,false);

        return new MyViewHolder(itemview);
    }



    @Override
    public int getItemCount() {
        return allPerson.size();
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //通过下标来获取这个对象
        Person person=allPerson.get(position);
        holder.Personname.setText(person.getName());
        //这个地方是三个表计算清楚给它传过去
        holder.Allmoney.setText("");
        holder.ifpay.setText("是否付清");

    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Personname,Allmoney;
        CheckedTextView ifpay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Personname=itemView.findViewById(R.id.PersonName);
            Allmoney=itemView.findViewById(R.id.Allmoney);
            ifpay=itemView.findViewById(R.id.ifpay);




        }
    }

}
