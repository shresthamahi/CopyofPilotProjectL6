package com.example.feb8;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;

import java.util.List;

public class MonitoringService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannels";
    public static final String CHANNEL_ID2 = "ForegroundServiceChannels";
    public static final String CHANNEL_ID3 = "ForegroundServiceChannels";
    float lightLimit;
    private SensorManager sensorManager;
    Sensor lightSensor,rotationVectorSensor;
    int LightInformed=0;
    int PostureInformed=0;
    int pitch;
    int roll;
    KeyguardManager keyguardManager;


    SensorEventListener lightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            lightLimit=values[0];
            if (lightLimit<5)
            {
                if (LightInformed==0) {
                    keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    if (!keyguardManager.isDeviceLocked()) {
                        addLightNotification();

                        LightInformed++;
                    }
                }
            }
            else
            {
                LightInformed=0;
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

            int safety=0;

            if (pitch>=(55) && pitch<=90 )
            {
                if (roll>=(-8) && roll<=8)
                {
                    safety=1;
                }

            }
            else if (roll<=(-55) && roll>=(-90))
            {
                if (pitch>=(-8) && pitch<=(8))
                {
                    safety=1;

                }
            }
            else
            {
                //do nth
            }

                if (safety!=1)
                {
                    if (PostureInformed==0){

                        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                        if (!keyguardManager.isDeviceLocked()) {
                            addPostureNotification();
                            PostureInformed++;
                        }
                    }
                }
                else
                    { PostureInformed=0; }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("FEB8 Foreground Service")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_eye)
                .build();

        startForeground(1,notification); //foreground notification started

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //start montitoring listeners
        sensorManager.registerListener(lightListener,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(rvListener,rotationVectorSensor,SensorManager.SENSOR_DELAY_NORMAL);



        return START_NOT_STICKY;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void addLightNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID2)
                .setSmallIcon(R.drawable.ic_bulbnotification)
                .setContentTitle("Light Notification from FEB 8")

                .setContentText("There is not enough light888")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("There is not enough light888"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, builder.build());
    }
    private void addPostureNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_posture_icon)
                .setContentTitle("Posture Notification")

                .setContentText("Your Posture is Incorrect")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your Posture is Incorrect"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(3, builder.build());
    }

    @Override
    public void onDestroy() {
        //imp to unregister sensors when the service is stopped because we dont want sensors after that
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(rvListener);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void addHandsFreeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "hands-freemode")
                .setSmallIcon(R.drawable.ic_hands_free_img)
                .setContentTitle("Use your phone in hands free mode")

                .setContentText("Hands free mode")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Use your phone in hands free mode"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(10, builder.build());
    }
}