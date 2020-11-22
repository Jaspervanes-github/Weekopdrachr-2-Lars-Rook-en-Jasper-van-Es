package com.example.philipshueweekopdracht;

public class Lamp {

    private String lampID;
    private String nameLamp;
    private boolean power;
    private int colorValueRed;
    private int colorValueGreen;
    private int colorValueBlue;

    public Lamp(String lampID, String name, boolean OnOrOff,int r, int g, int b){
        this.lampID = lampID;
        this.nameLamp = name;
        this.power = OnOrOff;
        this.colorValueRed = r;
        this.colorValueGreen = g;
        this.colorValueBlue = b;
    }

    public String getLampID() {
        return lampID;
    }

    public void setLampID(String lampID) {
        this.lampID = lampID;
    }

    public String getNameLamp() {
        return nameLamp;
    }

    public void setNameLamp(String nameLamp) {
        this.nameLamp = nameLamp;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public int getColorValueRed() {
        return colorValueRed;
    }

    public void setColorValueRed(int colorValueRed) {
        this.colorValueRed = colorValueRed;
    }

    public int getColorValueGreen() {
        return colorValueGreen;
    }

    public void setColorValueGreen(int colorValueGreen) {
        this.colorValueGreen = colorValueGreen;
    }

    public int getColorValueBlue() {
        return colorValueBlue;
    }

    public void setColorValueBlue(int colorValueBlue) {
        this.colorValueBlue = colorValueBlue;
    }
}
