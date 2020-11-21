package com.example.philipshueweekopdracht.ui;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;

import com.example.philipshueweekopdracht.Data;

public class Res extends Resources {

    private Data data = Data.getInstance();

    public Res(Resources original) {
        super(original.getAssets(), original.getDisplayMetrics(), original.getConfiguration());
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        return super.getColor(id, null);
    }

    @Override
    public int getColor(int id, Theme theme) throws NotFoundException {

        switch (getResourceEntryName(id)){
            case "fade_color":
                return Color.rgb(data.getColorValueRed(), data.getColorValueGreen(), data.getColorValueBlue());

            default:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return super.getColor(id, theme);
                }
                return super.getColor(id);
        }
    }
}
