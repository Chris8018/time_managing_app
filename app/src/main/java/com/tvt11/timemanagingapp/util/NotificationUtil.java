package com.tvt11.timemanagingapp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.activity.MainActivity;
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
                getBasicNotificationBuilder(context, CHANNEL_NAME_TIMER);

        nBuilder.setContentTitle(timerName)
                .setContentText(status)
                .setSound(notificationSound);

        NotificationManager nManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(
                    CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, NotificationManager.IMPORTANCE_LOW);
            nChannel.enableLights(true);
            nChannel.setLightColor(Color.BLUE);

            nManager.createNotificationChannel(nChannel);
        }

        nManager.notify(TIMER_ID, nBuilder.build());

    }

    public static void showTimerRunning(Context context, long wakeUpTime) {
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
                getBasicNotificationBuilder(context, CHANNEL_NAME_TIMER);

        nBuilder.setContentTitle(timerName)
                .setContentText("End at " + df.format(new Date(wakeUpTime)))
//                .setContentIntent(getPendingIntentWithStack(context, MainActivity.class))
                .setOngoing(true)
                .addAction(cancelAction)
                .addAction(finishAction);

        NotificationManager nManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(
                    CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, NotificationManager.IMPORTANCE_LOW);
            nChannel.enableLights(true);
            nChannel.setLightColor(Color.BLUE);

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

    private static <T> PendingIntent getPendingIntentWithStack(
            Context context,
            Class<T> javaclass
    ) {
        Intent resultIntent = new Intent(context, javaclass);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(javaclass);
        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
