package com.example.philipshueweekopdracht;

import android.graphics.Color;

public class Lamp {

    private String lampID;
    private String nameLamp;
    private boolean power;
    private int colorValueRed;
    private int colorValueGreen;
    private int colorValueBlue;
    private int hueValue;
    private int satValue;
    private int briValue;


    private int fadingSpeed;
    private int discoSpeed;

    private boolean fadingMode;
    private boolean discoMode;

    public Lamp(String lampID, String name, boolean OnOrOff,int r, int g, int b, int hue, int sat, int bri){
        this.lampID = lampID;
        this.nameLamp = name;
        this.power = OnOrOff;
        this.colorValueRed = r;
        this.colorValueGreen = g;
        this.colorValueBlue = b;
        this.hueValue = hue;
        this.satValue = sat;
        this.briValue = bri;

        System.out.println("R: " + r + " " + this.colorValueRed + " G: " + g + " " + this.colorValueGreen + " B: " + b + " " + this.colorValueBlue);
        System.out.println("H: " + this.hueValue + " S: " + this.satValue + " B: " + this.briValue);

//        calculateHSBColor();

        System.out.println("R: " + r + " " + this.colorValueRed + " G: " + g + " " + this.colorValueGreen + " B: " + b + " " + this.colorValueBlue);
        System.out.println("H: " + this.hueValue + " S: " + this.satValue + " B: " + this.briValue);

        this.fadingSpeed = 500;
        this.discoSpeed = 500;
        this.fadingMode = false;
        this.discoMode = false;


    }

    public int getHueValue() {
        return hueValue;
    }

    public void setHueValue(int hueValue) {
        this.hueValue = hueValue;
//        calculateRGBColor();
    }

    public int getSatValue() {
        return satValue;
    }

    public void setSatValue(int satValue) {
        this.satValue = satValue;
//        calculateRGBColor();
    }

    public int getBriValue() {
        return briValue;
    }

    public void setBriValue(int briValue) {
        this.briValue = briValue;
//        calculateRGBColor();
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

    public void setRGBValues(int red, int green, int blue){
        setColorValueRed(red);
        setColorValueGreen(green);
        setColorValueBlue(blue);
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
