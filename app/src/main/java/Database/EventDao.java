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
    @Query("SELECT * FROM EVENT_DATABASE ORDER BY ID")
    LiveData<List<Event>> getAllEventLive();
    @Query("SELECT * FROM EVENT_DATABASE ORDER BY ID")
    List<Event> getAllEvent();

    @Query("SELECT * FROM EVENT_DATABASE where id in(:eventId)")
    List<Event> findEventByEventId(List<Integer> eventId);

    @Query("SELECT * FROM EVENT_DATABASE where id in(:eventId)")
    Event findEventByEventId(int eventId);
}
