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
    @Query("SELECT * FROM person_database WHERE NAME= (:name)")
    Person findPersonMoney(String ... name);
    @Query("SELECT * FROM person_database")
    List<Person> findPerson();
    @Query("SELECT * FROM person_database where name in (:name)")
    Person findPersonByName(String ... name);
    @Query("SELECT name FROM person_database")
    List<String> findPersonName();
    @Query("SELECT * FROM person_database WHERE id IN (:id)")
    List<Person> findPersonById(List<Integer> id);
    @Query("SELECT * FROM person_database ORDER BY ID")
    LiveData<List<Person>> getAllPersonLive();

}
