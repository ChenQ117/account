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
    @Query("UPDATE Person SET money = (:money) WHERE name = (:name)")
    void updatePersonMoney(int money,String name);
    @Delete
    void deletePerson(Person...person);
    @Query("DELETE FROM PERSON WHERE NAME in (:names)")
    void deletePersonByName(String...names);
    @Query("SELECT money FROM Person WHERE NAME= (:name)")
    int findPersonMoney(int... name);
    @Query("SELECT * FROM PERSON ORDER BY ID")
    LiveData<List<Person>> getAllPersonLive();

}
