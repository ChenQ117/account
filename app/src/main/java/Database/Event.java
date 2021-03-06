package Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_database")
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "activity")
    private String activity;//活动名称
    @ColumnInfo(name = "time")
    private String time;//活动时间
    @ColumnInfo(name = "count")
    private int count;//人数
    @ColumnInfo(name = "amount")
    private int amount;//消费金额
    @ColumnInfo(name = "IsEmpty")
    private boolean isEmpty;//是否付清

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", time='" + time + '\'' +
                ", count=" + count +
                ", amount=" + amount +
                ", isEmpty=" + isEmpty +
                '}';
    }

    public Event() {
    }
    @Ignore
    public Event(String activity, int count, int amount) {
        this.activity = activity;
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
