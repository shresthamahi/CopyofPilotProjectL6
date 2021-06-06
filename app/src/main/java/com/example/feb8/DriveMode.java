package com.example.feb8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;


public class DriveMode extends Fragment {

    final static String DRIVE_STATE="non-active";
    int state=0;
    TextView t1;
    ToggleButton toggleButton;
    View rootview;
    public static int click=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            state = savedInstanceState.getInt(DRIVE_STATE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_drive_mode, container, false);
        toggleButton =(ToggleButton) rootview.findViewById(R.id.toggleButton);
        t1= (TextView)rootview.findViewById(R.id.textView2);

        return rootview;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DRIVE_STATE,state);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    state=1;
                    click=1;
                    toggleButton.setTextOn("Drive MODE ON");
                    t1.setText("The click state is:"+state);
                }
                else
                {
                    state=0;
                    click=0;
                    toggleButton.setTextOff("Drive MODE OFF");
                    t1.setText("The click state is:"+state);
                }
            }
        });

        }
}