package com.tvt11.timemanagingapp.util;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {
    private static final String CHANNEL_ID_TIMER = "";
    private static final String CHANNEL_NAME_TIMER = "";
    private static final int NOTIF_TIMER_ID = 0;

    public static void showTimerFinished(Context context) {
        // TODO: implement
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

    @TargetApi(26)
    private static void createNotificationChannel() {
        // TODO: implement
    }
}
