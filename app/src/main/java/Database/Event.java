package Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "activity")
    private String activity;//活动名称
    @ColumnInfo(name = "time")
    private String time;//活动时间
    @ColumnInfo(name = "count")
    private int count;//人数
    @ColumnInfo(name = "amount")
    private int amount;//消费金额

    public Event(String activity, String time, int count, int amount) {
        this.activity = activity;
        this.time = time;
        this.count = count;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
