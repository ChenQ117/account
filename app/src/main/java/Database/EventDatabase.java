package Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Event.class},version = 1,exportSchema = false)
public  abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao getEventDao();
    private static EventDatabase INSTANCE;
    static synchronized EventDatabase getEventDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,EventDatabase.class,"event_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
