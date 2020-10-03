package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.Person;

public class WhoPayAdapter extends RecyclerView.Adapter<WhoPayAdapter.MyViewHoder> {
    Map<CheckBox,EditText> mCheckBoxEditTextMap = new HashMap<>();
    List<Person> mPeople;

    public WhoPayAdapter(Map<CheckBox, EditText> checkBoxEditTextMap, List<Person> people) {
        mCheckBoxEditTextMap = checkBoxEditTextMap;
        mPeople = people;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_who_pay,null,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        Person person = mPeople.get(position);
        holder.mCheckBox.setText(person.getName());
        if(holder.mCheckBox.isChecked()){
            holder.mEditText.setVisibility(View.VISIBLE);
        }else {
            holder.mEditText.setVisibility(View.GONE);
        }
        mCheckBoxEditTextMap.put(holder.mCheckBox,holder.mEditText);
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }
    static class MyViewHoder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        EditText mEditText;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.checkBox);
            mEditText = itemView.findViewById(R.id.edit_money);
        }
    }

}
