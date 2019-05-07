package com.tvt11.timemanagingapp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {
    private static final String CHANNEL_ID_TIMER = "menu_timer";
    private static final String CHANNEL_NAME_TIMER = "Time Managing App Timer";
    private static final int TIMER_ID = 0;

    public static void showTimerFinished(Context context) {
        // TODO: implement
        Uri notificationSound =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder nBuilder =
                getBasicNotificationBuilder(context, CHANNEL_NAME_TIMER);

        nBuilder.setContentTitle("")
                .setContentText("")
                .setSound(notificationSound);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

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
        // TODO: implement
    }

    private static NotificationCompat.Builder getBasicNotificationBuilder(
            Context context,
            String channelID
    ) {
        // TODO: implement
        return null;
    }

    private static void hideTimerNotification(Context context) {
        // TODO: implement
    }

    private static <T> PendingIntent getPendingIntentWithStack(
            Context context,
            Class<T> javaclass
    ) {
        // TODO: implement
        return null;
    }
}
