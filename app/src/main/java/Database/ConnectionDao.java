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
    @Query("SELECT * FROM Connection ORDER BY ID")
    LiveData<List<Connection>> getAllConnectionLive();

}
