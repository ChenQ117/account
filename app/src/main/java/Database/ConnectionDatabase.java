package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Connection.class},version = 1,exportSchema = false)
public abstract class ConnectionDatabase extends RoomDatabase {
    public abstract ConnectionDao getConnectionDao();
    private static ConnectionDatabase INSTENCE;
    static synchronized ConnectionDatabase getConnectionDatabase(Context context){
        if (INSTENCE ==null){
            INSTENCE = Room.databaseBuilder(context.getApplicationContext(),ConnectionDatabase.class,"connection_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTENCE;
    }

}
