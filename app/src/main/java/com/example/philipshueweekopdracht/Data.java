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

    private ArrayList<Lamp> allLamps;

    public Data(){
        this.allLamps = new ArrayList<>();
    }


}
