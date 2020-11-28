package com.example.philipshueweekopdracht;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.philipshueweekopdracht.ui.ViewModel;
import com.example.philipshueweekopdracht.ui.fragments.MainFragment;

import java.util.ArrayList;

public class Data {
    private static Data data;

    public static Data getInstance(){
        if(data == null ){
            data = new Data();
            return data;
        }
        else {
            return data;
        }
    }

    private boolean dataSet;
    private ArrayList<Lamp> allLamps;
    private Lamp lampSelected;
    private ViewModel viewModel;
    private FragmentManager manager;
    private Fragment currentFragment;

    public Data(){
        this.allLamps = new ArrayList<>();
        this.lampSelected = null;
        this.dataSet = false;
        currentFragment = new MainFragment();
    }

    public boolean isDataSet() {
        return dataSet;
    }

    public void setDataSet(boolean dataSet) {
        this.dataSet = dataSet;
    }

    public ArrayList<Lamp> getAllLamps() {
        return allLamps;
    }

    public void setAllLamps(ArrayList<Lamp> allLamps) {
        this.allLamps = allLamps;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void updateViewModelLampList(){
        viewModel.setAllLamps(allLamps);
    }
    public void updateViewModelSelectedLamp(){
        viewModel.setLampSelected(lampSelected);
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public Lamp getLampSelected() {
        return lampSelected;
    }

    public void setLampSelected(Lamp lampSelected) {
        this.lampSelected = lampSelected;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
