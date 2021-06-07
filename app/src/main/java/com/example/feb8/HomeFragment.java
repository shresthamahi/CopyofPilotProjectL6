package com.example.feb8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;
import com.example.feb8.viewModel.MainAdapter;
import com.google.android.material.slider.Slider;

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;


public class HomeFragment extends Fragment {

    Switch slider;
    View rootView;
    TextView tv,tv2;
    ImageView im;
    private SensorManager sensorManager;
    Sensor rotationVectorSensor,lightSensor;
    int pitch;
    int roll;
    int monitoring = 0;
     final static String SWITCH_STATE = "0";
    int currentstate=0;
    private List<Habits> dataList;
    private Activity context;

    /**
     * For dashboard Purpose
     */
    public static int LightUpdateStatus=0;
    public static int PostureUpdateStatus=0;



    SensorEventListener lightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values =event.values;
            if (values[0]<5)
            {
                tv.setTextSize(30);
                tv.setBackgroundResource(R.color.melon);
                tv.setTextColor(getResources().getColor(R.color.amarathRed));
                tv.setText("TOO DARK TO USE YOUR PHONE");


                LightUpdateStatus=1;//if the user looks at dashboard when it is dark, it increases count
            }
            else
            {
                tv.setTextSize(30);
                tv.setBackgroundResource(R.color.teaGreen);
                tv.setTextColor(getResources().getColor(R.color.forestGreen));
                tv.setText("IT IS BRIGHT!");
                LightUpdateStatus=0;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    SensorEventListener rvListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] rotationMatrix = new float[16];
            SensorManager.getRotationMatrixFromVector(rotationMatrix,event.values);

            float[] remappedrotationMatrix = new float[16];
            SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_X,SensorManager.AXIS_Y,remappedrotationMatrix);

            float[] orientations= new float[3];
            SensorManager.getOrientation(remappedrotationMatrix,orientations);

            for(int i = 0; i < 3; i++) {
                orientations[i] = (float)(Math.toDegrees(orientations[i]));
            }
            pitch=Math.round(orientations[1])*(-1);
            roll=Math.round(orientations[2]);
            String msg="";
            int safety=0;

            msg += "Pitch:"+pitch+" Roll: "+roll;

            if (pitch>=(55) && pitch<=90 )
            {
                if (roll>=(-8) && roll<=8)
                {
                    msg += "Safe Portait";
                    safety=1;
                }
                else
                {
                    msg += "Unsafe Portait";
                }

            }
            else if (roll<=(-55) && roll>=(-90))
            {
                if (pitch>=(-8) && pitch<=(8))
                {
                    safety=1;
                    msg += "Safe Landscape";
                }
                else
                {
                    msg += "Unsafe Landscape";
                }
            }
            else
            {
                msg += "Overall Unsafe";
            }

            if (safety==1)
            {
                tv2.setTextSize(30);
                tv2.setBackgroundResource(R.color.teaGreen);
                tv2.setTextColor(getResources().getColor(R.color.forestGreen));
                tv2.setText("YOUR POSTURE IS \n SAFE");
                PostureUpdateStatus=0;
            }
            else
            {
                tv2.setTextSize(26);
                tv2.setBackgroundResource(R.color.melon);
                tv2.setTextColor(getResources().getColor(R.color.amarathRed));
                tv2.setText("YOUR POSTURE IS \n UNSAFE");
                PostureUpdateStatus=1;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            currentstate = savedInstanceState.getInt(SWITCH_STATE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            currentstate = savedInstanceState.getInt(SWITCH_STATE);

        }
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        im= rootView.findViewById(R.id.imageView);
        tv = rootView.findViewById(R.id.TVLightInfo);
        tv2 = rootView.findViewById(R.id.TVPostureInfo);
        slider = rootView.findViewById(R.id.switch1);
        im.setBackgroundResource(R.drawable.posture_animate);

        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor==null)
        {
            Toast.makeText(getActivity().getBaseContext(), "Error: No light sensor.", Toast.LENGTH_LONG).show();
            // finish();
        }
        if (rotationVectorSensor==null)
        {
            Toast.makeText(getActivity().getBaseContext(), "Error: No Rotation sensor.", Toast.LENGTH_LONG).show();
            // finish();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        AnimationDrawable frameAnimation = (AnimationDrawable) im.getBackground();
        frameAnimation.start();
        super.onStart();

    }

    @Override
    public void onResume() {
        slider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    currentstate=1;
                    startService();

                }
                else
                {
                    currentstate=0;
                    stopService();

                }
            }
        });
        sensorManager.registerListener(rvListener,rotationVectorSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lightListener,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    private void startService() {
        Intent serviceIntent = new Intent(getActivity(), MonitoringService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(getActivity(),MonitoringService.class);
        getActivity().stopService(serviceIntent);
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(rvListener);
        sensorManager.unregisterListener(lightListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(SWITCH_STATE,currentstate);
    }

}