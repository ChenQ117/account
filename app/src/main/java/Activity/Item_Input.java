package Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.account.R;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import Database.Event;
import Database.EventViewModel;

public class Item_Input extends AppCompatActivity {
    EditText editinput_01;//输入账单金额的Edit
    EditText editinput_02;//输入活动内容的Edit
    EditText editinput_03;//输入总人数的Edit
    Button button_input_people_01;//确定总人数
    Button bt_next;//下一步
    EventViewModel mEventViewModel;
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
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        bt_next = findViewById(R.id.button1);
        button_input_people_01=findViewById(R.id.button_input_people1);
        edit4 = new ArrayList<>();
        button_input_people_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editinput_03=findViewById(R.id.edit_input3);
                people_num=Integer.parseInt(editinput_03.getText().toString().trim());
                addView();//根据人数添加Edit框，输入人名
            }
        });
    }
    public void addView(){
        my_input_layout=findViewById(R.id.My_input_people_layout);
        for(int i=0;i<people_num;i++){
            editText1 = new EditText(this);
            editText1.setHint("人名");
            editText1.setWidth(300);
            editText1.setHeight(100);
            editText1.setTop(10);
            editText1.setSingleLine(true);
            editText1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            edit4.add(i,editText1);
            my_input_layout.addView(editText1);
        }
    }
    public void onClickNext(View view){
        editinput_01 = findViewById(R.id.edit_input1);
        editinput_02 = findViewById(R.id.edit_input2);
        editinput_03 = findViewById(R.id.edit_input3);
        String activityname = editinput_02.getText().toString().trim();
        String moneys = editinput_01.getText().toString().trim();
        String counts = editinput_03.getText().toString().trim();
        boolean flag = false;//用于判断edit4中的editText内容是否为空
        for (EditText edit:edit4){
            if("".equals(edit.getText().toString().trim())){
                flag = true;
                break;
            }
        }
        if(flag||"".equals(activityname)||"".equals(moneys)||"".equals(counts)){
            Toast.makeText(this,"信息未填写完整",Toast.LENGTH_SHORT).show();
        }else {
            int money = Integer.parseInt(moneys);
            int count = Integer.parseInt(counts);
            Event event = new Event(activityname,count,money);
            mEventViewModel.insertEvent(event);
        }
    }

}
