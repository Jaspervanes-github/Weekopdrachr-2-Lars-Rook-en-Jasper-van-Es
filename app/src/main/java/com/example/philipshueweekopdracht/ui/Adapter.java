package com.example.philipshueweekopdracht.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philipshueweekopdracht.Data;
import com.example.philipshueweekopdracht.Lamp;
import com.example.philipshueweekopdracht.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.LampViewHolder> {

    private Context context;
    private List<Lamp> allLamps;
    private OnItemClickListener clickListener;

    public Adapter(Context cntxt, List<Lamp> lamps, OnItemClickListener listener, Data data){
        this.context = cntxt;
        this.allLamps = lamps;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public LampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        LampViewHolder holder = new LampViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LampViewHolder holder, int position) {
        Lamp selectedLamp = allLamps.get(position);
        holder.title.setText(selectedLamp.getNameLamp() + "\n" + selectedLamp.getLampID());

        holder.onOrOffButton.setOnClickListener((view) -> {
            Lamp lamp = allLamps.get(position);
            //TODO: check if the request succeded!
            lamp.setPower(!lamp.isPower());
            if(lamp.isPower()){
                holder.onOrOffButton.setBackgroundColor(context.getColor(R.color.button_ON));
            }
            else{
                holder.onOrOffButton.setBackgroundColor(context.getColor(R.color.button_OFF));
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
        private Button deleteButton;


        public LampViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewItem);
            onOrOffButton = itemView.findViewById(R.id.buttonOnOrOff);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            clickListener.onItemClick(clickPosition);
        }
    }


}
