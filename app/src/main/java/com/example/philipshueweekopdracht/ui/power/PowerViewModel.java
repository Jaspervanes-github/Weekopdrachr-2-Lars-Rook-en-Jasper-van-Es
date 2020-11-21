package com.example.philipshueweekopdracht.ui.power;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PowerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PowerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is power fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}