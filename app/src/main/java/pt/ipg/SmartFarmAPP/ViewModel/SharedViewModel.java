package pt.ipg.SmartFarmAPP.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<CharSequence> text = new MutableLiveData<>();

    public void setText(CharSequence input) {
        text.setValue(input);
    }

    public LiveData<CharSequence> getText() {
        return text;
    }

}
