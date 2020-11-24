package com.example.philipshueweekopdracht;

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
}
