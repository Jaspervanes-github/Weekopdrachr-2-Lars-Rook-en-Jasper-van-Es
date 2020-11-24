package com.example.philipshueweekopdracht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.philipshueweekopdracht.ui.Res;
import com.example.philipshueweekopdracht.ui.fragments.MainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Res res;
    @Override
    public Resources getResources() {
        if(res == null){
            res = new Res(super.getResources());
        }
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);
        BottomNavigationView navView = findViewById(R.id.nav_viewer);
        navView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment(this)).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_power: {
                            //switch all lamps on/off
                        }
                        case R.id.navigation_refresh: {
                            //refresh current lamps
                        }
                    }
                    return true;
                }
            };

}