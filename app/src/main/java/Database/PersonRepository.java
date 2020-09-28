package Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonRepository {
    private  int a;
    private LiveData<List<Person>> allPersonsLive;
    private PersonDao mPersonDao;

    public LiveData<List<Person>> getAllPersonsLive() {
        return allPersonsLive;
    }

    public PersonDao getPersonDao() {
        return mPersonDao;
    }

    public PersonRepository(Context context) {
        PersonDatabase personDatabase = PersonDatabase.getPersonDatabase(context);
        mPersonDao = personDatabase.getPersonDao();
        allPersonsLive = mPersonDao.getAllPersonLive();
    }
    void insertPerson(Person...people){
        new InsertAsyncTask(mPersonDao).execute(people);
    }
    void updatePerson(Person...people){
        new UpdateAsyncTask(mPersonDao).execute(people);
    }
    void deletePerson(Person...people){
        new DeleteAsyncTask(mPersonDao).execute(people);
    }
    static class InsertAsyncTask extends AsyncTask<Person,Void,Void>{
        private PersonDao mPersonDao;
        public InsertAsyncTask(PersonDao personDao){
            mPersonDao = personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            mPersonDao.insertPerson(people);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Person,Void,Void>{
        private PersonDao mPersonDao;
        public UpdateAsyncTask(PersonDao personDao){
            mPersonDao = personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            mPersonDao.updatePerson(people);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Person,Void,Void>{
        private PersonDao mPersonDao;
        public DeleteAsyncTask(PersonDao personDao){
            mPersonDao = personDao;
        }
        @Override
        protected Void doInBackground(Person... people) {
            mPersonDao.deletePerson(people);
            return null;
        }
    }

}
