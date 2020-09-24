package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insertPerson(Person...person);
    @Update
    void updatePerson(Person...person);
    @Delete
    void deletePerson(Person...person);
    @Query("SELECT * FROM PERSON ORDER BY ID")
    LiveData<List<Person>> getAllPersonLive();

}
