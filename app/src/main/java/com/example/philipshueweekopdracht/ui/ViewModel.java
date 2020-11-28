package com.example.philipshueweekopdracht.ui;

import androidx.lifecycle.MutableLiveData;

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<ArrayList<Lamp>> allLamps;
    private MutableLiveData<Lamp> lampSelected;

    public ViewModel(){
        allLamps = new MutableLiveData<>();
        lampSelected = new MutableLiveData<>();
        this.allLamps.setValue(new ArrayList<>());
        this.lampSelected.setValue(null);
    }

    public MutableLiveData<Lamp> getLampSelected() {
        return lampSelected;
    }

    public void setLampSelected(Lamp lampSelected) {
        this.lampSelected.setValue(lampSelected);
    }


    public MutableLiveData<ArrayList<Lamp>> getAllLamps() {
        return allLamps;
    }

    public void setAllLamps(ArrayList<Lamp> lamps){
        this.allLamps.setValue(lamps);
    }

    public void addLamp(Lamp lamp){
        this.allLamps.getValue().add(lamp);
    }

    public void deleteLamp(Lamp lamp){
        this.allLamps.getValue().remove(lamp);
    }
    public void clearList(){
        this.allLamps.getValue().clear();
    }

    public void update(){
        setAllLamps(this.allLamps.getValue());
    }
}
