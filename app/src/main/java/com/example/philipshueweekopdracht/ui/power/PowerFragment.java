package com.example.philipshueweekopdracht.ui.power;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;
import com.example.philipshueweekopdracht.ui.Adapter;

public class PowerFragment extends Fragment implements Adapter.OnItemClickListener {

    private Data data = Data.getInstance();
    private RecyclerView recyclerView;
    private Adapter adapter;

    private PowerViewModel powerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_power, container, false);
        powerViewModel = new ViewModelProvider(this).get(PowerViewModel.class);
        //final TextView textView = root.findViewById(R.id.text_power);
        powerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        if(!data.isDataSet()){
            init();
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

    private void init(){
        data.getAllLamps().add(new Lamp("1", "Kitchen", true, 0, 255, 0));
        data.getAllLamps().add(new Lamp("2", "Bedroom", true, 255, 0, 0));
        data.getAllLamps().add(new Lamp("3", "Livingroom", true, 0, 0, 255));
        data.getAllLamps().add(new Lamp("1", "Kitchen", true, 0, 255, 0));
        data.getAllLamps().add(new Lamp("2", "Bedroom", true, 255, 0, 0));
        data.getAllLamps().add(new Lamp("3", "Livingroom", true, 0, 0, 255));
        data.getAllLamps().add(new Lamp("1", "Kitchen", true, 0, 255, 0));
        data.getAllLamps().add(new Lamp("2", "Bedroom", true, 255, 0, 0));
        data.getAllLamps().add(new Lamp("3", "Livingroom", true, 0, 0, 255));
        data.getAllLamps().add(new Lamp("1", "Kitchen", true, 0, 255, 0));
        data.getAllLamps().add(new Lamp("2", "Bedroom", true, 255, 0, 0));
        data.getAllLamps().add(new Lamp("3", "Livingroom", true, 0, 0, 255));
        data.getAllLamps().add(new Lamp("1", "Kitchen", true, 0, 255, 0));
        data.getAllLamps().add(new Lamp("2", "Bedroom", true, 255, 0, 0));
        data.getAllLamps().add(new Lamp("3", "Livingroom", true, 0, 0, 255));
    }

    @Override
    public void onItemClick(int clickPosition) {

    }
}