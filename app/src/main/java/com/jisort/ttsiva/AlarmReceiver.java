package com.jisort.ttsiva;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

/**
 * Created by sikinijj on 2/26/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Context context1;
    @Override
    public void onReceive(Context context, Intent intent) {
        context1= context;
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        showForegroundNotification("You have a lesson in 30 minutes time! Prepare");
    }

    private void showForegroundNotification(String contentText)
    {

        Vibrator v = (Vibrator) context1.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);


        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context1, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_access_alarm)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentText(contentText);

        Intent resultIntent = new Intent(context1, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context1);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context1.getSystemService(Context.NOTIFICATION_SERVICE);
        Random ran = new Random();
        int x = ran.nextInt(100) + 10000;
        mNotificationManager.notify(x, mBuilder.build());





    }
}
