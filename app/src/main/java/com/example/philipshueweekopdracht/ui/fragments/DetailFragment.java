package com.example.philipshueweekopdracht.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;
import com.example.philipshueweekopdracht.ui.Adapter;
import com.example.philipshueweekopdracht.ui.ViewModel;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements LifecycleOwner {

    private ViewModel viewModel;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getLampSelected().observe(this.getViewLifecycleOwner(), lampObserver);
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        this.view = root;

        //TODO: connect the layout components to the correct values, which they need to be displayed.

        return root;
    }


    Observer<Lamp> lampObserver = new Observer<Lamp>() {
        @Override
        public void onChanged(Lamp lamp) {
        //TODO: fill this up for the selected Lamp to keep the detail screen up to date.

        }
    };

}