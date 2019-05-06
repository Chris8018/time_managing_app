package com.tvt11.timemanagingapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tvt11.timemanagingapp.receiver.TimerFinishedReceiver;

import java.util.Calendar;

public class AlarmControl {

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static long setAlarm(Context context, long currentTime, long timeRemain) {
        long wakeUpTime = currentTime + timeRemain;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimerFinishedReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent);

        PrefUtil.setAlarmSetTime(currentTime, context);
        return 0;
    }

    public static long removeAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimerFinishedReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.cancel(pendingIntent);

        PrefUtil.setAlarmSetTime(0, context);
        return 0;
    }
}
