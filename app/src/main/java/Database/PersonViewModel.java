package Database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    public PersonDao mPersonDao;
    public PersonRepository mPersonRepository;
    public PersonDao getPersonDao() {
        return mPersonDao;
    }

    public LiveData<List<Person>> getAllPersonLive(){
        return mPersonRepository.getAllPersonsLive();
    }
    public PersonViewModel(@NonNull Application application) {
        super(application);
        PersonDatabase personDatabase = PersonDatabase.getPersonDatabase(application);
        mPersonDao= personDatabase.getPersonDao();
        mPersonRepository = new PersonRepository(application);
    }
    public void insertPerson(Person...people){
        mPersonRepository.insertPerson(people);
    }
    public void updatePerson(Person...people){
        mPersonRepository.updatePerson(people);
    }
    public void deletePerson(Person...people){
        mPersonRepository.deletePerson(people);
    }
}
