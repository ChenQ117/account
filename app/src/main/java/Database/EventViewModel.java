package Database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository mEventRepository;
    private EventDao mEventDao;

    public EventDao getEventDao() {
        return mEventDao;
    }

    public LiveData<List<Event>> getAllEventLive(){
        return mEventRepository.getAllEventLive();
    }
    public EventViewModel(@NonNull Application application) {
        super(application);
        EventDatabase eventDatabase = EventDatabase.getEventDatabase(application);

        mEventRepository = new EventRepository(application);
        mEventDao = eventDatabase.getEventDao();
    }
    public void insertEvent(Event...events){
        mEventRepository.insertEvent(events);
    }
    public void deleteEvent(Event...events){
        mEventRepository.deleteEvent(events);
    }
    public void updateEvent(Event...events){
        mEventRepository.updateEvent(events);
    }

}
