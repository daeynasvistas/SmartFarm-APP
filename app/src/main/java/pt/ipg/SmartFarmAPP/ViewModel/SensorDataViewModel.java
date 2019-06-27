package pt.ipg.SmartFarmAPP.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Repository;
import pt.ipg.SmartFarmAPP.Entity.SensorData;

public class SensorDataViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<SensorData>> allSensorData;

    public SensorDataViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allSensorData = repository.getAllSensorDatas();
    }

    public void insert(SensorData sensorData) { repository.insert(sensorData);  }

    // receber todos os valores // não é preciso CRUD só R
    public LiveData<List<SensorData>> getAllSensorData() {
        return allSensorData;
    }



}