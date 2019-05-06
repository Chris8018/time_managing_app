package com.tvt11.timemanagingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tvt11.timemanagingapp.activity.MainActivity.TimerState;

public class PrefUtil {

    private static final String TIMER_ID =
            "com.tvt11.timemanagingapp.timer_id";

    public static int getTimerID(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(TIMER_ID, 0);
    }

    public static void setTimerId(int id, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(TIMER_ID, id);
        editor.apply();
    }

    private static final String TIMER_STATE_ID =
            "com.tvt11.timemanagingapp.timer_state_id";

    public static TimerState getTimerState(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int ordinal = preferences.getInt(TIMER_STATE_ID, 0);
        return TimerState.values()[ordinal];
    }

    public static void setTimerState(TimerState timerState, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(TIMER_STATE_ID, timerState.ordinal());
        editor.apply();
    }

    private static final String TIMER_NAME_ID =
            "com.tvt11.timemanagingapp.timer_name_id";

    public static String getTimerName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(TIMER_NAME_ID, "No Timer");
    }

    public static void setTimerName(String name, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(TIMER_NAME_ID, name);
        editor.apply();
    }

    private static final String TIME_REMAIN_ID =
            "com.tvt11.timemanagingapp.time_remain_id";

    public static long getTimeRemain(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(TIME_REMAIN_ID, 0);
    }

    public static void setTimeRemain(long timeRemain, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(TIME_REMAIN_ID, timeRemain);
        editor.apply();
    }

    private static final String ALARM_SET_TIME_ID =
            "com.tvt11.timemanagingapp.alarm_set_time_id";

    public static long getAlarmSetTime(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(ALARM_SET_TIME_ID, 0);
    }

    public static void setAlarmSetTime(long time, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(ALARM_SET_TIME_ID, time);
        editor.apply();
    }
}
