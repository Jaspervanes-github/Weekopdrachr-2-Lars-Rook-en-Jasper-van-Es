package com.example.philipshueweekopdracht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.philipshueweekopdracht.ui.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Data data;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);
        BottomNavigationView navView = findViewById(R.id.nav_viewer);
        navView.setOnNavigationItemSelectedListener(navListener);

        data = Data.getInstance();

        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        data.setViewModel(viewModel);
        data.setManager(getSupportFragmentManager());
        data.getManager().beginTransaction().replace(R.id.fragment_container, data.getCurrentFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_power: {
                            //switch all lamps on/off
                            Lamp lamp = new Lamp(counter + "", "lamp" + counter, true, 0, 0, 0);
                            data.getAllLamps().add(lamp);
                            data.updateViewModelLampList();
                            counter++;
                        }
                        case R.id.navigation_refresh: {
                            //refresh current lamps
                        }
                    }
                    return true;
                }
            };

}