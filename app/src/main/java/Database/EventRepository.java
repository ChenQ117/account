package Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private EventDao mEventDao ;
    private LiveData<List<Event>> allEventLive;

    public EventDao getEventDao() {
        return mEventDao;
    }

    public LiveData<List<Event>> getAllEventLive() {
        return allEventLive;
    }

    public EventRepository(Context context) {
        EventDatabase eventDatabase = EventDatabase.getEventDatabase(context);
        mEventDao = eventDatabase.getEventDao();
        allEventLive = mEventDao.getAllEventLive();
    }
    public void insertEvent(Event...events){
        new InsertAsyncTask(mEventDao).doInBackground(events);
    }
    static class InsertAsyncTask extends AsyncTask<Event,Void,Void>{
        private EventDao mEventDao;
        public InsertAsyncTask(EventDao eventDao) {
            mEventDao = eventDao;
        }
        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.insertEvent(events);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Event,Void,Void>{
        private EventDao mEventDao;
        public UpdateAsyncTask (EventDao eventDao){
            mEventDao = eventDao;
        }
        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.updateEvent(events);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Event,Void,Void>{
        private EventDao mEventDao;

        public DeleteAsyncTask(EventDao eventDao) {
            mEventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.deleteEvent(events);
            return null;
        }
    }
}
