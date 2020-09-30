package Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.account.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Item_Input extends AppCompatActivity {
    EditText editinput_01;//输入账单金额的Edit
    EditText editinput_02;//输入活动内容的Edit
    EditText editinput_03;//输入总人数的Edit
    Button button_input_people_01;//确定总人数
    Button button1;
    int people_num=0;
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
                if(people_num==0){
                    people_num = Integer.parseInt(editinput_03.getText().toString());
                    addView();//根据人数添加Edit框，输入人名
                }else {
                    deleteView();
                    people_num = Integer.parseInt(editinput_03.getText().toString());
                    addView();//根据人数添加Edit框，输入人名
                }


            }
        });
    }
    public void addView(){
        my_input_layout=findViewById(R.id.My_input_people_layout);
        for(int i=0;i<people_num;i++){
            editText1 = new EditText(this);
            Log.d(TAG, "addView: -----------"+editText1.toString());
            editText1.setHint("人名");
            editText1.setWidth(300);
            editText1.setHeight(100);
            editText1.setTop(10);
            editText1.setSingleLine(true);
            editText1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            edit4.add(i,editText1);
            Log.d(TAG, "addView: -----------"+people_num);
            my_input_layout.addView(editText1);
        }
    }
    public void deleteView() {
        while (people_num!=0){
            EditText editText = edit4.get(people_num-1);
            my_input_layout.removeView(editText);
            edit4.remove(people_num-1);

            people_num--;
        }


    }


}
