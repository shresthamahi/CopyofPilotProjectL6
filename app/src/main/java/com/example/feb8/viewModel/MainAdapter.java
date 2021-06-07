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
import com.example.feb8.IncomingCallReceiver;
import com.example.feb8.R;
import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Habits> dataList;
    private Activity context;
    private RoomDB database;


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
        }

        if (HomeFragment.PostureUpdateStatus ==1)
        {
            int oldval= data.getPostureInfo();
            int newval= oldval+1;
            database.mainDao().updatePostureInfo(newval);

        }

        if (IncomingCallReceiver.silentstatus ==1)
        {
            int oldval= data.getDrivingInfo();
            int newval= oldval+1;
            database.mainDao().updateDriveCallInfo(newval);

        }


        String temp= Integer.toString(data.getLightInfo());
        String temp2= Integer.toString(data.getPostureInfo());
        String temp3= Integer.toString(data.getDrivingInfo());

        holder.textViewLight.setText(temp);
        holder.textViewPosture.setText(temp2);
        holder.textViewDriveCalls.setText(temp3);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initializing variable
        TextView textViewLight,textViewPosture,textViewDriveCalls;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLight= itemView.findViewById(R.id.tvLight);
            textViewPosture = itemView.findViewById(R.id.tvPosture);
            textViewDriveCalls = itemView.findViewById(R.id.tvDriveCalls);
        }
    }


}
