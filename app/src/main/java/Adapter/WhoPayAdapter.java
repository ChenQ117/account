package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.Person;

public class WhoPayAdapter extends RecyclerView.Adapter<WhoPayAdapter.MyViewHoder> {
    Map<CheckBox,EditText> mCheckBoxEditTextMap = new HashMap<>();

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_who_pay,null,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
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
