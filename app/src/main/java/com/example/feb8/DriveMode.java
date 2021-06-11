package com.example.feb8;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import static android.content.Context.MODE_PRIVATE;


public class DriveMode extends Fragment {

    final static String DRIVE_STATE="non-active";
    int state=0;
    TextView t1;
    ToggleButton toggleButton;
    View rootview;
    public static int click=0;
    ImageView imageView;

    /**
     * For shared Preferences
     */
    public static final String SHARED_PREFS= "sharedPrefs";
    public static final String BUTTON_STATE= "button";
    public boolean ButtonOnOff;

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
        imageView = (ImageView) rootview.findViewById(R.id.imageView3);
        imageView.setBackgroundResource(R.drawable.car_moving_animation);

        loadData();
        updateViews();

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DRIVE_STATE,state);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    state=1;
                    click=1;
                    toggleButton.setTextOn("Drive MODE ON");

                    frameAnimation.start();


                }
                else
                {
                    frameAnimation.stop();
                    state=0;
                    click=0;
                    toggleButton.setTextOff("Drive MODE OFF");

                }
            }
        });

        }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(BUTTON_STATE, toggleButton.isChecked());
        editor.apply();
    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        ButtonOnOff= sharedPreferences.getBoolean(BUTTON_STATE,false);
    }

    public void updateViews()
    {
        toggleButton.setChecked(ButtonOnOff);
        if (ButtonOnOff)
        {
            toggleButton.setText("Drive MODE ON");
        }
        else
        {
            toggleButton.setText("Drive MODE OFF");
        }

    }
}