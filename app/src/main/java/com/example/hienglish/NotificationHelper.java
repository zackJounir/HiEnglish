package com.example.hienglish;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    private static final String CHANNEL_ID = "notificationChannel";
    private static final String CHANNEL_NAME = "reminderChannel";
    private NotificationManager mNotificationManager;

    public NotificationHelper(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            creatChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    void creatChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        getmNotificationManager().createNotificationChannel(channel);
    }

    public NotificationManager getmNotificationManager(){
        if(mNotificationManager == null){
            mNotificationManager = getSystemService(NotificationManager.class);
        }
        return mNotificationManager;
    }
    public NotificationCompat.Builder getNotification(){
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle("Hi English")
                .setContentText("Let's learn a new word!")
                .setSmallIcon(R.mipmap.icon_luncher);
    }
}
