package Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ConnectionViewModel extends AndroidViewModel {
    private ConnectionDao mConnectionDao;
    private ConnectionDatabase mConnectionDatabase;
    private ConnectionRepository mConnectionRepository;
    public LiveData<List<Connection>> getAllConnectionLive(){
        return mConnectionDao.getAllConnectionLive();
    }

    public ConnectionDao getConnectionDao() {
        return mConnectionDao;
    }

    public ConnectionViewModel(@NonNull Application application) {
        super(application);
        mConnectionRepository = new ConnectionRepository(application);
        mConnectionDatabase = ConnectionDatabase.getConnectionDatabase(application);
        mConnectionDao = mConnectionDatabase.getConnectionDao();
    }
    public void insertConnection(Connection...connections){
        mConnectionRepository.insertConnection(connections);
    }
    public void deleteConnection(Connection...connections){
        mConnectionRepository.deleteConnection(connections);
    }
    public void updateConnection(Connection...connections){
        mConnectionRepository.updateConnection(connections);
    }
}
