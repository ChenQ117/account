package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
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
    @Query("SELECT * FROM Event ORDER BY ID")
    LiveData<List<Event>> getAllEventLive();
}
