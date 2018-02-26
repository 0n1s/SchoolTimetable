package com.jisort.ttsiva;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by jjsikini on 11/16/17.
 */



public class NotifyService extends Service {


    int mStartMode;


    IBinder mBinder;

    MediaPlayer m = new MediaPlayer();
    public void playBeep()
    {
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }
            AssetFileDescriptor descriptor = getAssets().openFd("now.wav");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(true);
            m.start();
        } catch (Exception e) {
            Log.d("Exception", String.valueOf(e));
        }
    }


    @Override
    public void onCreate()
    {


        showForegroundNotification("You have a class in 30 minute's time. Please check your schedule.");



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  Toast.makeText(this, "Service onstart", Toast.LENGTH_SHORT).show();
        return mStartMode;

    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
       // Toast.makeText(this, "on bindviewholder", Toast.LENGTH_SHORT).show();
        return mBinder;
    }



    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }


    private void showForegroundNotification(String contentText)
    {




        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentText(contentText);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random ran = new Random();
        int x = ran.nextInt(100) + 10000;







    }
}
