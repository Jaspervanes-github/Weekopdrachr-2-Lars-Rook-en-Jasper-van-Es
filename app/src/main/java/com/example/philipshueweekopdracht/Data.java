package com.example.philipshueweekopdracht;

import com.example.philipshueweekopdracht.ui.ViewModel;

import java.util.ArrayList;

public class Data {
    private static Data data;

    public static Data getInstance(){
        if(data == null ){
            data = new Data();
            return data;
        }
        else {
            return data;
        }
    }

    private boolean dataSet;
    private ArrayList<Lamp> allLamps;
    private ViewModel viewModel;

    public Data(){
        this.allLamps = new ArrayList<>();
        this.dataSet = false;
    }

    public boolean isDataSet() {
        return dataSet;
    }

    public void setDataSet(boolean dataSet) {
        this.dataSet = dataSet;
    }

    public ArrayList<Lamp> getAllLamps() {
        return allLamps;
    }

    public void setAllLamps(ArrayList<Lamp> allLamps) {
        this.allLamps = allLamps;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
