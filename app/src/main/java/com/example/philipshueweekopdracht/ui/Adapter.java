package com.example.philipshueweekopdracht.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philipshueweekopdracht.ColorCalculator;
import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.LampViewHolder> {

    private Context context;
    private List<Lamp> allLamps;
    private OnItemClickListener clickListener;

    public Adapter(Context cntxt, List<Lamp> lamps, OnItemClickListener listener) {
        this.context = cntxt;
        this.allLamps = lamps;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public LampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamp_list_item, parent, false);
        LampViewHolder holder = new LampViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LampViewHolder holder, int position) {
        Lamp selectedLamp = allLamps.get(position);
        holder.title.setText(selectedLamp.getNameLamp() + "\n" + selectedLamp.getLampID());
        holder.layout.getBackground().setTint(ColorCalculator.getIntFromColor(selectedLamp.getColorValueRed(), selectedLamp.getColorValueGreen(), selectedLamp.getColorValueBlue()));

        if (selectedLamp.isPower()) {
            holder.onOrOffButton.setBackgroundColor(Color.GREEN);
        } else {
            holder.onOrOffButton.setBackgroundColor(Color.RED);
        }

        holder.onOrOffButton.setOnClickListener((view) -> {
            //TODO: check if the request succeded!
            selectedLamp.setPower(!selectedLamp.isPower());
            if (selectedLamp.isPower()) {
                // holder.onOrOffButton.setBackgroundColor(context.getColor(R.color.button_ON));
                holder.onOrOffButton.setBackgroundColor(Color.GREEN);
                Data.getInstance().getClient().turnLampOn(position);
            } else {
                holder.onOrOffButton.setBackgroundColor(Color.RED);
                Data.getInstance().getClient().turnLampOff(position);
            }
            if (Data.getInstance().getLampSelected() != null) {
                if (Data.getInstance().getLampSelected().isFadingMode()) {
                    Data.getInstance().getLampSelected().setFadingMode(false);
                    Data.getInstance().getClient().stopFadingOfLamp();
                } else if (Data.getInstance().getLampSelected().isDiscoMode()) {
                    Data.getInstance().getLampSelected().setDiscoMode(false);
                    Data.getInstance().getClient().stopDiscoOfLamp();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allLamps.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int clickPosition);
    }


    public class LampViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        private Button onOrOffButton;
        private View layout;


        public LampViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewItem);
            onOrOffButton = itemView.findViewById(R.id.buttonOnOrOff);
            layout = itemView.findViewById(R.id.list_item_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            clickListener.onItemClick(clickPosition);
        }
    }

}
