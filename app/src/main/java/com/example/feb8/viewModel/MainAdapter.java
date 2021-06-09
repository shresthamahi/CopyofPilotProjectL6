package com.example.feb8.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public static final String TAG="Checker";

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

        holder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().ResetValues();
                holder.tvinfo.setBackgroundResource(R.color.melon);

                holder.tvinfo.setText("Please reopen this page to view actions");

            }
        });
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

        Log.d(TAG,"the silent status is:"+IncomingCallReceiver.silentstatus);
        if (IncomingCallReceiver.silentstatus ==1)
        {

            int oldval= data.getDrivingInfo();
            int newval= oldval+1;
            database.mainDao().updateDriveCallInfo(newval);
        }
        IncomingCallReceiver.silentstatus=5;
        Log.d(TAG,"the UPDATED silent status is:"+IncomingCallReceiver.silentstatus);

        if (IncomingCallReceiver.NearEar ==1)
        {
            int oldval= data.getHandsFreeCallInfo();
            int newval= oldval+1;
            database.mainDao().updateHandsFreeMode(newval);

        }


        String temp= Integer.toString(data.getLightInfo());
        String temp2= Integer.toString(data.getPostureInfo());
        String temp3= Integer.toString(data.getDrivingInfo());
        String temp4= Integer.toString(data.getHandsFreeCallInfo());


        holder.textViewLight.setText(temp);
        holder.textViewPosture.setText(temp2);
        holder.textViewDriveCalls.setText(temp4);
        holder.tvHandsFreeCall.setText(temp3);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initializing variable
        TextView textViewLight,textViewPosture,textViewDriveCalls,tvHandsFreeCall,tvinfo;
        Button btnReset;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLight= itemView.findViewById(R.id.tvLight);
            textViewPosture = itemView.findViewById(R.id.tvPosture);
            textViewDriveCalls = itemView.findViewById(R.id.tvDriveCalls);
            tvHandsFreeCall= itemView.findViewById(R.id.tvHandsFreeCall);
            btnReset = itemView.findViewById(R.id.buttonReset);
            tvinfo = itemView.findViewById(R.id.tvInfo);
        }
    }


}
