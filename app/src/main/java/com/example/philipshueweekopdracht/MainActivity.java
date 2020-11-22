package com.example.philipshueweekopdracht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.philipshueweekopdracht.ui.Adapter;
import com.example.philipshueweekopdracht.ui.Res;
import com.example.philipshueweekopdracht.ui.power.PowerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Data data = Data.getInstance();
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
        setContentView(R.layout.activity_botom_navigationview);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);



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