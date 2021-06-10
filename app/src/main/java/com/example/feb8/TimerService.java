package com.example.feb8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerService extends Service {

    public static final String CHANNEL_ID3 = "TimerServiceChannels";
    public static final String CHANNEL_ID = "TimerServiceChannels";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer is active")
                .setContentText("Your Timer CountDown...")

                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(1,notification); //foreground notification started

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addTimeNotification();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void addTimeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID3)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Your time is up!!")

                .setContentText("Screen time exceeded")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Screen time exceeded"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(55, builder.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}