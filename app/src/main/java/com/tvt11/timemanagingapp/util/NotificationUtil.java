package com.tvt11.timemanagingapp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.receiver.NotificationActionReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationUtil {
    private static final String TAG = "NotificationUtil";

    private static final String CHANNEL_ID_TIMER = "timeM_app_timer";
    private static final String CHANNEL_NAME_TIMER = "Time Managing App Timer";
    private static final int TIMER_ID = 0;

    public static void showTimerStopped(Context context, String status) {
        Uri notificationSound =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String timerName = PrefUtil.getTimerName(context);

        NotificationCompat.Builder nBuilder =
                getBasicNotificationBuilder(context, CHANNEL_ID_TIMER);

        nBuilder.setContentTitle(timerName)
                .setContentText(status)
                .setSound(notificationSound);

        NotificationManager nManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(
                    CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, NotificationManager.IMPORTANCE_LOW);

            nManager.createNotificationChannel(nChannel);
        }

        nManager.notify(TIMER_ID, nBuilder.build());

    }

    public static void showTimerRunning(Context context, long wakeUpTime) {
        // TODO: change this to show time left until finish
        Intent cancelIntent = new Intent(context, NotificationActionReceiver.class);
        cancelIntent.setAction(AppConstants.ACTION_CANCEL);

        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(
                context, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action cancelAction =
                new NotificationCompat.Action(0, "Cancel", cancelPendingIntent);

        Intent finishIntent = new Intent(context, NotificationActionReceiver.class);
        finishIntent.setAction(AppConstants.ACTION_FINISH);

        PendingIntent finishPendingIntent = PendingIntent.getBroadcast(
                context, 1, finishIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action finishAction =
                new NotificationCompat.Action(0, "Finish", finishPendingIntent);

        String timerName = PrefUtil.getTimerName(context);

        DateFormat df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

        NotificationCompat.Builder nBuilder =
                getBasicNotificationBuilder(context, CHANNEL_ID_TIMER);

        nBuilder.setContentTitle(timerName)
                .setContentText("End at " + df.format(new Date(wakeUpTime)))
                .setUsesChronometer(true)
                .setOngoing(true)
                .addAction(cancelAction)
                .addAction(finishAction);

        NotificationManager nManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Android Version is >= Oreo");
            NotificationChannel nChannel = new NotificationChannel(
                    CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, NotificationManager.IMPORTANCE_DEFAULT);

            nManager.createNotificationChannel(nChannel);
        }

        nManager.notify(TIMER_ID, nBuilder.build());
    }

    public static void hideTimerNotification(Context context) {
        NotificationManager nManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.cancel(TIMER_ID);
    }

    private static NotificationCompat.Builder getBasicNotificationBuilder(
            Context context, String channelID
    ) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_timer)
                .setAutoCancel(true)
                .setDefaults(0);
        return nBuilder;
    }
}
