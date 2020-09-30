package com.example.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    EditText editinput_01;//输入账单金额的Edit
    EditText editinput_02;//输入活动内容的Edit
    EditText editinput_03;//输入总人数的Edit
    Button button_input_people_01;//确定总人数
    int people_num;
    LinearLayout my_input_layout;//添加人名的布局
    ArrayList<EditText> edit4;//记录人名
    EditText editText1;//新建Edit
    private static final String TAG = "Main3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_layout);
        editinput_01=findViewById(R.id.edit_input1);
        editinput_02=findViewById(R.id.edit_input2);
        editinput_03=findViewById(R.id.edit_input3);
        button_input_people_01=findViewById(R.id.button_input_people1);
        edit4 = new ArrayList<>();
        button_input_people_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_num=Integer.parseInt(editinput_03.getText().toString());
                addView();//根据人数添加Edit框，输入人名
            }
        });
    }
    public void addView(){

        for(int i=0;i<people_num;i++){
            my_input_layout=findViewById(R.id.My_input_people_layout);
            editText1 = new EditText(this);
            Log.d(TAG, "addView: -----------"+editText1.toString());
            editText1.setHint("人名");
            editText1.setWidth(300);
            editText1.setHeight(100);
            editText1.setTop(10);
            editText1.setSingleLine(true);
            editText1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            edit4.add(people_num,editText1);
            Log.d(TAG, "addView: -----------"+people_num);
            my_input_layout.addView(editText1);
        }
    }
}
