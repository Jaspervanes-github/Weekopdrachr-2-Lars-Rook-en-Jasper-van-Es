package com.example.philipshueweekopdracht.ui.refresh;

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

import com.example.philipshueweekopdracht.R;

public class RefreshFragment extends Fragment {

    private RefreshViewModel refreshViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        refreshViewModel =
                new ViewModelProvider(this).get(RefreshViewModel.class);
        View root = inflater.inflate(R.layout.fragment_refresh, container, false);
        final TextView textView = root.findViewById(R.id.text_refresh);
        refreshViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}