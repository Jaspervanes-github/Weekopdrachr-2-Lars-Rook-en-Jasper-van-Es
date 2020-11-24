package com.example.philipshueweekopdracht.ui;

import androidx.lifecycle.MutableLiveData;

import com.example.philipshueweekopdracht.Lamp;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<Lamp> lampSelected;



    public MutableLiveData<Lamp> getLampSelected() {
        return lampSelected;
    }

    public void setLampSelected(MutableLiveData<Lamp> lampSelected) {
        this.lampSelected = lampSelected;
    }
}
