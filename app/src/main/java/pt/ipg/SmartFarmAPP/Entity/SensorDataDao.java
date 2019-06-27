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

    @Query("SELECT * FROM sensorData_table ORDER BY id ASC")
    LiveData<List<SensorData>> getAllSensorData();  // Observe Dados !!! com LiveData



}
