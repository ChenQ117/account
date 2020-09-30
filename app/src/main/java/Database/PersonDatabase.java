package Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Person.class},version = 2,exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao getPersonDao();
    private static PersonDatabase INSTANCE;
    static synchronized PersonDatabase getPersonDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PersonDatabase.class,"person_database.db")
                    .build();
        }
        return INSTANCE;
    }
//    static final Migration MIGRATION1_2 = new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("DROP TABLE person_database");
//        }
//    };

}
