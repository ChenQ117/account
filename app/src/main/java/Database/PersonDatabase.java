package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Person.class},version = 1,exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao getPersonDao();
    private static PersonDatabase INSTANCE;
    static synchronized PersonDatabase getPersonDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PersonDatabase.class,"person_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
