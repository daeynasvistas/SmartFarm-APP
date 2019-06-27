package pt.ipg.SmartFarmAPP.Entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SensorDataDao {


    @Insert
    void insert(SensorData  sensorData);

    @Update
    void update(SensorData sensorData);

    @Delete
    void delete(SensorData sensorData);

    @Query("DELETE FROM sensorData_table")
    void deleteAllSensorData();

    @Query("SELECT * FROM sensorData_table ORDER BY date_of_value DESC")
    LiveData<List<SensorData>> getAllSensorData();  // Observe Dados !!! com LiveData


    @Query("SELECT * FROM sensorData_table WHERE id= :sensor AND mac LIKE :node_ID ORDER BY date_of_value DESC LIMIT :limit") // -- select cada sensor individualmente e enviar dados com limit
    LiveData<List<SensorData>> getAllSensorDatasSpecific(int sensor, int limit, String node_ID);  //

}
