package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tvt11.timemanagingapp.activity.MainActivity;
import com.tvt11.timemanagingapp.util.PrefUtil;

public class TimerFinishedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: show notification

        PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
        PrefUtil.setAlarmSetTime(0, context);
    }
}
