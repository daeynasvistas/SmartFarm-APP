package pt.ipg.SmartFarmAPP.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.Entity.Repository;

public class PictureViewModel extends AndroidViewModel {


    private Repository repository;
    private LiveData<List<Picture>> allPictures;

    public PictureViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allPictures = repository.getAllPictures();
    }

    public void insert(Picture picture) { repository.insert(picture);  }

    public void update(Picture picture) {
        repository.update(picture);
    }

    public void delete(Picture picture) {
        repository.delete(picture);
    }

    public void deleteAllPictures() {
        repository.deleteAllPictures();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }


}
