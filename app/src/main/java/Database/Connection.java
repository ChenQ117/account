package Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 第三张表，用来连接活动和人
 * 根据活动id找到参与的人或者根据人的id找到对应的活动
 */
@Entity(tableName = "connection_database")
public class Connection {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "event_id")
    private int eventId;//活动id
    @ColumnInfo(name = "person_Id")
    private int personId;//人名id
    @ColumnInfo(name = "singlemoney")
    private int singlemoney;//单笔活动的消费金额

    public Connection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSinglemoney() {
        return singlemoney;
    }

    public void setSinglemoney(int singlemoney) {
        this.singlemoney = singlemoney;
    }
    @Ignore
    public Connection(int eventId, int personId) {
        this.eventId = eventId;
        this.personId = personId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
