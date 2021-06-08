package com.example.feb8;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationClass {
    Activity context;
    public void addHandsFreeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "hands-freemode")
                .setSmallIcon(R.drawable.ic_hands_free_img)
                .setContentTitle("Use your phone in hands free mode")

                .setContentText("Hands free mode")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Use your phone in hands free mode"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(17, builder.build());
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "newID",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
