package com.example.feb8;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.Method;


public class IncomingCallReceiver extends BroadcastReceiver {

    /**
     * For dashboard Purpose
     */
    public static int silentstatus=0;

    int drivingMode=99;
    AudioManager audioManager;

    SensorManager sensorManager;
    Sensor proximitySensor;

    final public static String TAG = "Proximity Sensor";

   NotificationClass nc;

    SensorEventListener proxiListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0]==0)
            {
                Log.d(TAG,"Over here inside proxmity Sensors");
              nc.createNotificationChannel();
              nc.addHandsFreeNotification();

            }
            else
            {

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        drivingMode=DriveMode.click;
        try{
            Class c= Class.forName(tm.getClass().getName());
            int state = tm.getCallState();

            if (state==1)
            {
                Toast.makeText(context, "The status of driving mode is "+ drivingMode, Toast.LENGTH_SHORT).show();

                if (drivingMode==1) {
                    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    silentstatus=1;
                }
                else
                {
                    silentstatus=0;
                }

            }
            else if(state ==2)
            {

                Toast.makeText(context, "Call is received. call state is "+ state, Toast.LENGTH_SHORT).show();
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setSpeakerphoneOn(true);

                if (proximitySensor == null) {
                    Toast.makeText(context, "No proximity sensor found in device.", Toast.LENGTH_SHORT).show();

                } else {
                    // registering our sensor with sensor manager.
                    sensorManager.registerListener(proxiListener,
                            proximitySensor,
                            SensorManager.SENSOR_DELAY_NORMAL); }



            }
            else if(state==0)
            {

                Toast.makeText(context, "Call is Cut nOw. call state is "+ state, Toast.LENGTH_SHORT).show();
                sensorManager.unregisterListener(proxiListener);
            }
            else
            {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}
