package com.example.philipshueweekopdracht;

import android.graphics.Color;

public class ColorCalculator {

    public static int[] calculateRGBColor(int hue, int sat, int bri) {
        int color = Color.HSVToColor(new float[]{
                (float) ((hue / 65535.0) * 360.0),
                (float) (sat / 255.0),
                (float) (bri / 255.0)});

        int[] rgb = new int[3];

        rgb[0] = Color.red(color);
        rgb[1] = Color.green(color);
        rgb[2] = Color.blue(color);

        return rgb;
    }

    public static int[] calculateHSBColor(int red, int green, int blue) {
        float[] hueSatBri = new float[3];

        Color.RGBToHSV(red, green, blue, hueSatBri);

        int[] hsb = new int[3];
        hsb[0] = Math.round((hueSatBri[0] / 360) * 65535);
        hsb[1] = Math.round(hueSatBri[1] * 255);
        hsb[2] = Math.round(hueSatBri[2] * 255);

        return hsb;
    }

    public static int getIntFromColor(int Red, int Green, int Blue) {
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

}
