package Database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private LiveData<List<Event>> allEventLive;
    private EventRepository mEventRepository;
    public EventViewModel(@NonNull Application application) {
        super(application);
    }


}
