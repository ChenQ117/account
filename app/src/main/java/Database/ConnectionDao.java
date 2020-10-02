package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ConnectionDao{
    @Insert
    void insertConnection(Connection...connections);
    @Update
    void updateConnection(Connection...connections);
    @Delete
    void deleteConnection(Connection...connections);
    @Query("SELECT * FROM connection_database ORDER BY ID")
    LiveData<List<Connection>> getAllConnectionLive();
    @Query("SELECT * FROM connection_database")
    List<Connection> getAllConnection();
    @Query("DELETE FROM connection_database WHERE event_id IN (:eventid)")
    void deleteConnectionByEventId(int...eventid);
    @Query("DELETE FROM connection_database WHERE person_Id IN (:personid)")
    void deleteConnectionByPersonId(int ... personid);
    @Query("SELECT person_Id FROM connection_database WHERE event_id IN (:eventid)")
    List<Integer> findConnectionByEventId(int...eventid);
    @Query("SELECT event_id FROM connection_database WHERE person_Id IN (:personid)")
    List<Integer> findConnectionByPersonId(int ... personid);
}
