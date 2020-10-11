package Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;

import java.util.List;
import java.util.Map;

import Database.Person;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.MyViewHoder> {
    Map<CheckBox,EditText> mCheckBoxEditTextMap;
    List<Person> mPeople;
    int allMoney;
    int amoney;

    public PayAdapter(Map<CheckBox, EditText> checkBoxEditTextMap, List<Person> people,int money) {
        mCheckBoxEditTextMap = checkBoxEditTextMap;
        mPeople = people;
        allMoney = money;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_who_pay2,parent,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, int position) {
        Person person = mPeople.get(position);
        holder.mCheckBox.setText(person.getName());
        holder.mEditText.setVisibility(View.GONE);

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.mEditText.setVisibility(View.VISIBLE);
                }else {
                    holder.mEditText.setVisibility(View.GONE);
                }
            }
        });
        holder.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String moneys = holder.mEditText.getText().toString().trim();
                holder.mEditText.setHint(String.valueOf(allMoney-amoney));
                int x = 0;
                if(!"".equals(moneys)){
                    x = Integer.parseInt(moneys);
                }
                if(hasFocus){
                    amoney -= x;
                }else {
                    amoney += x;
                    if(amoney>allMoney){
                        Toast.makeText(v.getContext(),"金额错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
