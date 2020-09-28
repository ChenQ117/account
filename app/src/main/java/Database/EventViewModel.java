package Database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
private  int a;
    private EventRepository mEventRepository;
    public LiveData<List<Event>> getAllEventLive(){
        return mEventRepository.getAllEventLive();
    }
    public EventViewModel(@NonNull Application application) {
        super(application);
        mEventRepository = new EventRepository(application);
    }
    void insertEvent(Event...events){
        mEventRepository.insertEvent(events);
    }
    void deleteEvent(Event...events){
        mEventRepository.deleteEvent(events);
    }
    void updateEvent(Event...events){
        mEventRepository.updateEvent(events);
    }

}
