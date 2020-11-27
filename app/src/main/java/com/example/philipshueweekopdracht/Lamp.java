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

    public Lamp(String lampID, String name, boolean OnOrOff, int r, int g, int b) {
        this.lampID = lampID;
        this.nameLamp = name;
        this.power = OnOrOff;
        this.colorValueRed = r;
        this.colorValueGreen = g;
        this.colorValueBlue = b;

        calculateHSBColor();
    }

    public int getHueValue() {
        return hueValue;
    }

    public void setHueValue(int hueValue) {
        this.hueValue = hueValue;
        calculateRGBColor();
    }

    public int getSatValue() {
        return satValue;
    }

    public void setSatValue(int satValue) {
        this.satValue = satValue;
        calculateRGBColor();
    }

    public int getBriValue() {
        return briValue;
    }

    public void setBriValue(int briValue) {
        this.briValue = briValue;
        calculateRGBColor();
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

    private void calculateRGBColor() {
        int color = Color.HSVToColor(new float[]{
                (float) ((this.hueValue / 65535.0) * 360.0),
                (float) (this.satValue / 255.0),
                (float) (this.briValue / 255.0)});
        setColorValueRed(Color.red(color));
        setColorValueGreen(Color.green(color));
        setColorValueBlue(Color.blue(color));
    }

    private void calculateHSBColor(){
        float[] hsb = {};
        Color.RGBToHSV(this.colorValueRed,this.colorValueGreen,this.colorValueBlue,hsb);

        setHueValue((int)hsb[0]);
        setSatValue((int)hsb[1]);
        setBriValue((int)hsb[2]);
    }
}
