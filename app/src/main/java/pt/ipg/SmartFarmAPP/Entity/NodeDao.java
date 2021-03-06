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
public interface NodeDao {

    @Insert
    void insert(Node node);

    @Update
    void update(Node node);

    @Delete
    void delete(Node node);

    @Query("DELETE FROM node_table")
    void deleteAllNodes();

    @Query("SELECT * FROM node_table ORDER BY id ASC")
    LiveData<List<Node>> getAllNodes();  // Observe Dados !!! com LiveData

    @Query("SELECT * FROM node_table WHERE id=0")
    List<Node> getUnsyncLocalNodes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNodes(List<Node> nodes);

    @Query("SELECT * FROM node_table")
    List<Node> getNodes();

}
