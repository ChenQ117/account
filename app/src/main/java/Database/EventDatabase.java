package Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Event.class},version = 3,exportSchema = false)
public  abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao getEventDao();
    private static EventDatabase INSTANCE;
    static synchronized EventDatabase getEventDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),EventDatabase.class,"event_database")
                    .addMigrations(MIGRATION2_3).build();
        }
        return INSTANCE;
    }
    static final Migration MIGRATION2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE Event");
        }
    };
}
