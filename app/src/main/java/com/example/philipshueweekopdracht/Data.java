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
    private Client client;

    public Data(){
        this.client = new Client();
        this.allLamps = new ArrayList<>();
    }


}
