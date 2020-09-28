package Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ConnectionRepository {
    private ConnectionDao mConnectionDao;
    private ConnectionDatabase mConnectionDatabase;
    private LiveData<List<Connection>> allConnectionLive;

    public LiveData<List<Connection>> getAllConnectionLive() {
        return allConnectionLive;
    }

    public ConnectionDao getConnectionDao() {
        return mConnectionDao;
    }

    public ConnectionRepository (Context context){
        mConnectionDatabase = ConnectionDatabase.getConnectionDatabase(context);
        mConnectionDao = mConnectionDatabase.getConnectionDao();
        allConnectionLive = mConnectionDao.getAllConnectionLive();
    }
    void insertConnection (Connection...connections){
        new InsertAsyncTask(mConnectionDao).execute(connections);
    }
    void deleteConnection (Connection...connections){
        new DeleteAsyncTask(mConnectionDao).execute(connections);
    }
    void updateConnection (Connection...connections){
        new UpdateAsyncTask(mConnectionDao).execute(connections);
    }
    static class InsertAsyncTask extends AsyncTask<Connection,Void,Void>{
        private ConnectionDao mConnectionDao;
        public InsertAsyncTask(ConnectionDao connectionDao){
            mConnectionDao = connectionDao;
        }
        @Override
        protected Void doInBackground(Connection... connections) {
            mConnectionDao.insertConnection(connections);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Connection,Void,Void>{
        private ConnectionDao mConnectionDao;
        public DeleteAsyncTask(ConnectionDao connectionDao){
            mConnectionDao = connectionDao;
        }
        @Override
        protected Void doInBackground(Connection... connections) {
            mConnectionDao.deleteConnection(connections);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Connection,Void,Void>{
        private ConnectionDao mConnectionDao;
        public UpdateAsyncTask(ConnectionDao connectionDao){
            mConnectionDao = connectionDao;
        }
        @Override
        protected Void doInBackground(Connection...connections){
            mConnectionDao.updateConnection(connections);
            return null;
        }
    }
}
