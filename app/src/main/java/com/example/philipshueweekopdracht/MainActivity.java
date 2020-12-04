package com.example.philipshueweekopdracht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.philipshueweekopdracht.ui.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Data data;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);
        Data.getInstance().setContext(this);
        BottomNavigationView navView = findViewById(R.id.nav_viewer);
        navView.setOnNavigationItemSelectedListener(navListener);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        data = Data.getInstance();

        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        data.setViewModel(viewModel);
        data.setManager(getSupportFragmentManager());
        data.getManager().beginTransaction().replace(R.id.fragment_container, data.getCurrentFragment()).commit();

        if(!data.getClient().isConnected)
        data.getClient().Connect();
        data.getClient().getAllLamps();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_power: {
                            //switch all lamps on/off
                            data.getClient().setPowerOfAllLamps(data.isAllPowerOn());
                            data.setAllPowerOn(!data.isAllPowerOn());

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    data.updateViewModelSelectedLamp();
                                    data.updateViewModelLampList();
                                }
                            });
                        }
                        case R.id.navigation_refresh: {
                            //refresh current lamps
                            data.getClient().getAllLamps();
                        }
                    }
                    return true;
                }
            };
}