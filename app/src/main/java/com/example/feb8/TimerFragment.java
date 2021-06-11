package com.example.feb8;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class TimerFragment extends Fragment {

    static final String MINUTE_STATE = "0";
    final static String ALARAM_STATE="active";
    public static final String NEWSHARED_PREFS= "sharedPrefs";

    public boolean newclick=false;
    TextView mins,tvmsg;
    Button setTimer;
    FloatingActionButton increaseTime,decreaseTime;
    View rootview;
    static int minute=0;
    String txtToConcat="0";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            txtToConcat = savedInstanceState.getString(MINUTE_STATE);

        }

        rootview = inflater.inflate(R.layout.timer_fragment, container, false);
        mins= (TextView) rootview.findViewById(R.id.tvtimer);
        increaseTime= (FloatingActionButton) rootview.findViewById(R.id.btnIncrease);
        decreaseTime = (FloatingActionButton) rootview.findViewById(R.id.btnDecrease);
        setTimer = (Button) rootview.findViewById(R.id.btnStartTimer);
        mins.setText(txtToConcat);
        tvmsg = (TextView) rootview.findViewById(R.id.tvMessage);

        loadData();
        updateViews();

        return rootview;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        increaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (minute<=40) {
                   minute = minute + 1;
                   txtToConcat = Integer.toString(minute);
                   mins.setText(txtToConcat);
               }
            }
        });

        decreaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (minute!=0){
                minute= minute - 1;
                txtToConcat = Integer.toString(minute);
                mins.setText(txtToConcat); }
            }
        });

        setTimer.setOnClickListener(new View.OnClickListener() {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
            @Override
            public void onClick(View v) {

                int preferredTime= Integer.parseInt(mins.getText().toString());
                if (preferredTime!=0){
                    tvmsg.setText("You will be alerted by alaram sound in "+preferredTime+" minutes");
                    preferredTime *= 1000*60;


                    new CountDownTimer(8000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            setTimer.setEnabled(false);
                            newclick=true;
                        }

                        public void onFinish() {
                            setTimer.setEnabled(true);
                            r.play();
                            newclick=false;
                        }
                    }.start();
                }


            }
        });
    }

    public void startAlert() {

            int i =3;
            Intent intent = new Intent(getContext(), TimerBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getActivity(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (i * 1000), pendingIntent);
            Toast.makeText(getContext(), "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();

    }

    public void alarmSet()
    {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
        r.play();
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(MINUTE_STATE,txtToConcat);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NEWSHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(ALARAM_STATE, newclick);
        editor.apply();
    }


    public void loadData()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NEWSHARED_PREFS,MODE_PRIVATE);
        newclick= sharedPreferences.getBoolean(ALARAM_STATE,false);
    }

    public void updateViews()
    {
        Log.d("newTag","the value of alaram on is:"+newclick);
       //setTimer.setEnabled(newclick);
       if (newclick)
       {
       setTimer.setBackgroundColor(getResources().getColor(R.color.crayolaGreen));
       }

    }
}