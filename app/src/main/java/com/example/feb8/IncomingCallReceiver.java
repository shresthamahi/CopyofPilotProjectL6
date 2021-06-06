package com.example.feb8;


import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.Method;


public class IncomingCallReceiver extends BroadcastReceiver {


    int drivingMode=99;
    AudioManager audioManager;
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
                ////////////////////////////////////////////////////
                if (drivingMode==1) {
                    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                ////////////////////////////////////////////////////
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
