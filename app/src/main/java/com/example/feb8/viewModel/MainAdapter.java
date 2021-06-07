package com.example.feb8.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feb8.HomeFragment;
import com.example.feb8.R;
import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Habits> dataList;
    private Activity context;
    private RoomDB database;
    int lightVal=0;

    public MainAdapter(Activity context,List<Habits> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_main,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initializig Habits
        Habits data = dataList.get(position);
        //initialize database
        database = RoomDB.getInstance(context);
        if (HomeFragment.LightUpdateStatus ==1)
        {
            int oldval= data.getLightInfo();
            int newval= oldval+1;
            database.mainDao().updateLightInfo(newval);
            data.setLightInfo(newval);
        }

        String temp= Integer.toString(data.getLightInfo());

        holder.textViewLight.setText(temp);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initializing variable
        TextView textViewLight;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLight= itemView.findViewById(R.id.tvLight);
        }
    }

    public void UpdateLightInfo()
    {
        this.lightVal=1;

    }
}
