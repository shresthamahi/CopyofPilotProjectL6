package com.example.feb8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class TimerFragment extends Fragment {

    static final String MINUTE_STATE = "0";
    public static final String CHANNEL_ID3 = "ForegroundServiceChannels";
    public static final String CHANNEL_ID = "ForegroundServiceChannels";
    TextView mins;
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
        return rootview;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        increaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (minute<=40) {
                   minute = minute + 5;
                   txtToConcat = Integer.toString(minute);
                   mins.setText(txtToConcat);
               }
            }
        });

        decreaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (minute!=0){
                minute= minute -5;
                txtToConcat = Integer.toString(minute);
                mins.setText(txtToConcat); }
            }
        });

        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(6000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                       createNotificationChannel();
                       addTimeNotification();
                    }
                }.start();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void addTimeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID3)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Your time is up!!")

                .setContentText("Screen time exceeded")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Screen time exceeded"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(4, builder.build());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(MINUTE_STATE,txtToConcat);
    }
}