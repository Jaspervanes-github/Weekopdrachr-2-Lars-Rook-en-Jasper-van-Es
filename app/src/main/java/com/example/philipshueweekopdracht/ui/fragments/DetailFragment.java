package com.example.philipshueweekopdracht.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;
import com.example.philipshueweekopdracht.ui.Adapter;
import com.example.philipshueweekopdracht.ui.ViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements LifecycleOwner {

    private Data data;
    private ViewModel viewModel;
    private View view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        this.view = root;
        this.data = Data.getInstance();
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getLampSelected().observe(this.getViewLifecycleOwner(), lampObserver);

        //TODO: connect the layout components to the correct values, which they need to be displayed.

        setAllComponents();
        SeekBar seekBarR = view.findViewById(R.id.detailSeekBarR);
        seekBarR.setProgress(data.getLampSelected().getColorValueRed());

        SeekBar seekBarG = view.findViewById(R.id.detailSeekBarG);
        seekBarG.setProgress(data.getLampSelected().getColorValueGreen());

        SeekBar seekBarB = view.findViewById(R.id.detailSeekBarB);
        seekBarB.setProgress(data.getLampSelected().getColorValueBlue());

        Button powerButton = view.findViewById(R.id.detailButtonOnOff);
        if(data.getLampSelected().isPower()){
            powerButton.setBackgroundColor(Color.GREEN);
        } else{
            powerButton.setBackgroundColor(Color.RED);
        }

        return root;
    }


    Observer<Lamp> lampObserver = new Observer<Lamp>() {
        @Override
        public void onChanged(Lamp lamp) {
        //TODO: fill this up for the selected Lamp to keep the detail screen up to date.
            setAllComponents();
        }
    };


    private void setAllComponents(){

       updateComponents();

        Button backButton = view.findViewById(R.id.detailButtonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.getInstance().getManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
            }
        });

        Button powerButton = view.findViewById(R.id.detailButtonOnOff);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newPower = !data.getLampSelected().isPower();
                data.getLampSelected().setPower(newPower);
                if(data.getLampSelected().isPower()){
                    powerButton.setBackgroundColor(Color.GREEN);
                } else{
                    powerButton.setBackgroundColor(Color.RED);
                }
                data.updateViewModelSelectedLamp();
            }
        });

        EditText editTextName = view.findViewById(R.id.detailEditTextName);
        editTextName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ){
                    data.getLampSelected().setNameLamp(editTextName.getText().toString());
                    data.updateViewModelSelectedLamp();
                    editTextName.setText("");
                    return true;
                }
                return false;
            }
        });

        SeekBar seekBarR = view.findViewById(R.id.detailSeekBarR);
        seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                data.getLampSelected().setColorValueRed(progress);
                data.updateViewModelSelectedLamp();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekBarG = view.findViewById(R.id.detailSeekBarG);
        seekBarG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                data.getLampSelected().setColorValueGreen(progress);
                data.updateViewModelSelectedLamp();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekBarB = view.findViewById(R.id.detailSeekBarB);
        seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                data.getLampSelected().setColorValueBlue(progress);
                data.updateViewModelSelectedLamp();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void updateComponents(){
        TextView test = view.findViewById(R.id.detailTextViewTitle);
        test.setText(data.getLampSelected().getNameLamp());

        TextView textViewRValue = view.findViewById(R.id.detailTextViewRValue);
        textViewRValue.setText(data.getLampSelected().getColorValueRed() + "");

        TextView textViewGValue = view.findViewById(R.id.detailTextViewGValue);
        textViewGValue.setText(data.getLampSelected().getColorValueGreen() + "");

        TextView textViewBValue = view.findViewById(R.id.detailTextViewBValue);
        textViewBValue.setText(data.getLampSelected().getColorValueBlue() + "");

        ImageView colorView = view.findViewById(R.id.detailImageViewColor);


        colorView.setBackgroundColor(getIntFromColor(data.getLampSelected().getColorValueRed(), data.getLampSelected().getColorValueGreen(), data.getLampSelected().getColorValueBlue()));

    }

    private int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}