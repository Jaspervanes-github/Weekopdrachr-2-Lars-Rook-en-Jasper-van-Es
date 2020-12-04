package com.example.philipshueweekopdracht.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.MainActivity;
import com.example.philipshueweekopdracht.R;
import com.example.philipshueweekopdracht.ui.Adapter;
import com.example.philipshueweekopdracht.ui.ViewModel;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class MainFragment extends Fragment implements Adapter.OnItemClickListener, LifecycleOwner {

    private Data data;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private MainActivity main;
    private Context context;
    private MainFragment mainFragment;

   // public MainFragment(MainActivity mainActivity){
    //    main = mainActivity;
    //}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        data  = Data.getInstance();
        this.context = root.getContext();
        this.mainFragment = this;
        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        data.setViewModel(viewModel);
        data.getViewModel().getAllLamps().observe(mainFragment.getViewLifecycleOwner(), listOfLampsObserver);

        if(!data.isDataSet()){
            //init();
            recyclerView = root.findViewById(R.id.lampListRV);
            adapter = new Adapter(root.getContext(), data.getAllLamps(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            data.setDataSet(true);
        }
        else{
            recyclerView = root.findViewById(R.id.lampListRV);
            adapter = new Adapter(root.getContext(), data.getAllLamps(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        }
        return root;
    }

    @Override
    public void onItemClick(int clickPosition) {
        //data.getViewModel().setLampSelected(data.getViewModel().getAllLamps().getValue().get(clickPosition));
        data.setLampSelected(data.getAllLamps().get(clickPosition));
        data.updateViewModelSelectedLamp();
        System.out.println(data.getLampSelected().getNameLamp()+ "   " + data.getLampSelected().getColorValueRed() + "   " + data.getLampSelected().getColorValueGreen() + "   " + data.getLampSelected().getColorValueBlue());
        Fragment newFragment = new DetailFragment();
        data.setCurrentFragment(newFragment);
        data.getManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();
        //main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailFragment()).commit();
    }

    Observer<ArrayList<Lamp>> listOfLampsObserver = new Observer<ArrayList<Lamp>>(){
        @Override
        public void onChanged(ArrayList<Lamp> lamps) {
            adapter = new Adapter(context, lamps, mainFragment);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
    };



}