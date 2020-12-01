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

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.LampViewHolder> {

    private Context context;
    private List<Lamp> allLamps;
    private OnItemClickListener clickListener;

    public Adapter(Context cntxt, List<Lamp> lamps, OnItemClickListener listener){
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
        holder.layout.getBackground().setTint(getIntFromColor(selectedLamp.getColorValueRed(), selectedLamp.getColorValueGreen(), selectedLamp.getColorValueBlue()));

        if(selectedLamp.isPower()){
            holder.onOrOffButton.setBackgroundColor(Color.GREEN);
        } else{
            holder.onOrOffButton.setBackgroundColor(Color.RED);
        }

        holder.onOrOffButton.setOnClickListener((view) -> {
            //TODO: check if the request succeded!
            selectedLamp.setPower(!selectedLamp.isPower());
            if(selectedLamp.isPower()){
               // holder.onOrOffButton.setBackgroundColor(context.getColor(R.color.button_ON));
                holder.onOrOffButton.getBackground().setTint(Color.GREEN);
            }
            else{
                //holder.onOrOffButton.setBackgroundColor(context.getColor(R.color.button_OFF));
                holder.onOrOffButton.getBackground().setTint(Color.RED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allLamps.size();
    }




    public interface OnItemClickListener{
        void onItemClick(int clickPosition);
    }


    public class LampViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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


    private int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }


}
