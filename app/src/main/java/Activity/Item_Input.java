package Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.account.R;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import Database.Connection;
import Database.Event;
import Database.EventDao;
import Database.EventDatabase;
import Database.EventViewModel;
import Database.Person;
import Database.PersonDao;
import Database.PersonDatabase;
import Database.PersonViewModel;

public class Item_Input extends AppCompatActivity {
    EditText editinput_01;//输入账单金额的Edit
    EditText editinput_02;//输入活动内容的Edit
    EditText editinput_03;//输入总人数的Edit
    EventViewModel mEventViewModel;
    EventDatabase mEventDatabase;
    EventDao mEventDao;
    PersonViewModel mPersonViewModel;
    PersonDatabase mPersonDatabase;
    PersonDao mPersonDao;
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
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventDatabase = Room.databaseBuilder(this,EventDatabase.class,"event_database").build();
        mEventDao = mEventDatabase.getEventDao();
        mPersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        mPersonDatabase = Room.databaseBuilder(this,PersonDatabase.class,"person_database").build();
        mPersonDao = mPersonDatabase.getPersonDao();
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
    public void onClickNext(View view){
        editinput_01 = findViewById(R.id.edit_input1);
        editinput_02 = findViewById(R.id.edit_input2);
        editinput_03 = findViewById(R.id.edit_input3);
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        String activityname = editinput_02.getText().toString().trim();
        String counts = editinput_03.getText().toString().trim();
        String moneys = editinput_01.getText().toString().trim();
        int flag = 1;//用于判断人名是否为空
        for(EditText edit:edit4){
            if("".equals(edit.getText().toString().trim())){
                flag = 2;
                break;
            }
        }
        if(flag == 2 ||"".equals(activityname)||"".equals(counts)||"".equals(moneys)){
            Toast.makeText(this,"信息未填写完整",Toast.LENGTH_SHORT).show();
        }else {
            //设置消费活动表
            int count = Integer.parseInt(counts);
            int money = Integer.parseInt(moneys);
            Event event = new Event(activityname,count,money );
            mEventViewModel.insertEvent(event);

            //设置人表
            String[] personname = new String[edit4.size()];
            for(int i=0;i<edit4.size();i++){
                personname[i] =  edit4.get(i).getText().toString().trim();
            }
            List<Person> personList = mPersonDao.findPerson(personname);
            for (int i=0;i<personname.length;i++){
                if(!personList.contains(personname[i])){
                    Person person = new Person(personname[i],0);
                    mPersonViewModel.insertPerson(person);
                }
            }

            //设置参加记录表
            //int event_id = mEventDao.getPrimaryKey();
            //System.out.println(event_id+"----------------------");
        }
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
    public void deleteView() {
        while (people_num!=0){
            EditText editText = edit4.get(people_num-1);
            my_input_layout.removeView(editText);
            edit4.remove(people_num-1);
            people_num--;
        }
    }


}
