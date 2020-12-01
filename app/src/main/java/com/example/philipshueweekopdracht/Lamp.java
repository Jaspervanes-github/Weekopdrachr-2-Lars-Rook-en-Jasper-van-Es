package com.example.philipshueweekopdracht;

public class Lamp {

    private String lampID;
    private String nameLamp;
    private boolean power;
    private int colorValueRed;
    private int colorValueGreen;
    private int colorValueBlue;

    private int fadingSpeed;
    private int discoSpeed;

    private boolean fadingMode;
    private boolean discoMode;

    public Lamp(String lampID, String name, boolean OnOrOff,int r, int g, int b){
        this.lampID = lampID;
        this.nameLamp = name;
        this.power = OnOrOff;
        this.colorValueRed = r;
        this.colorValueGreen = g;
        this.colorValueBlue = b;

        this.fadingSpeed = 500;
        this.discoSpeed = 500;
        this.fadingMode = false;
        this.discoMode = false;
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

    public int getFadingSpeed() {
        return fadingSpeed;
    }

    public void setFadingSpeed(int fadingSpeed) {
        this.fadingSpeed = fadingSpeed;
    }

    public int getDiscoSpeed() {
        return discoSpeed;
    }

    public void setDiscoSpeed(int discoSpeed) {
        this.discoSpeed = discoSpeed;
    }

    public boolean isFadingMode() {
        return fadingMode;
    }

    public void setFadingMode(boolean fadingMode) {
        this.fadingMode = fadingMode;
    }

    public boolean isDiscoMode() {
        return discoMode;
    }

    public void setDiscoMode(boolean discoMode) {
        this.discoMode = discoMode;
    }
}
