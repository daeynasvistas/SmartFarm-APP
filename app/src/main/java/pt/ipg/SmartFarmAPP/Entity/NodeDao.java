package pt.ipg.SmartFarmAPP.Entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NodeDao {

    @Insert
    void insert(Node node);

    @Update
    void update(Node node);

    @Delete
    void delete(Node node);

    @Query("DELETE FROM node_table")
    void deleteAllNodes();

    @Query("SELECT * FROM node_table ORDER BY model DESC")
    LiveData<List<Node>> getAllNodes();  // Observe Dados !!! com LiveData
}
