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
public interface PictureDao {
    @Insert
    void insert(Picture picture);

    @Update
    void update(Picture picture);

    @Delete
    void delete(Picture picture);

    @Query("DELETE FROM picture_table")
    void deleteAllPictures();

    @Query("SELECT * FROM picture_table ORDER BY Local_ID DESC")
    LiveData<List<Picture>> getAllPictures();  // Observe Dados !!! com LiveData


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPictures(List<Picture> picture);

    @Query("SELECT * FROM picture_table")
    List<Picture> getPictures();

}
