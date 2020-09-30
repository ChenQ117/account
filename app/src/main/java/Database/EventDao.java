package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insertEvent(Event...events);
    @Update
    void updateEvent(Event...events);
    @Delete
    void deleteEvent(Event...events);
    @Query("SELECT * FROM EVENT_DATABASE ORDER BY ID")
    LiveData<List<Event>> getAllEventLive();
    //@RawQuery("SELECT LAST_INSERT_ID() FROM event_database";null)
    //int getPrimaryKey();
}
