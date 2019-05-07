package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tvt11.timemanagingapp.activity.MainActivity;
import com.tvt11.timemanagingapp.util.AlarmControl;
import com.tvt11.timemanagingapp.util.AppConstants;
import com.tvt11.timemanagingapp.util.NotificationUtil;
import com.tvt11.timemanagingapp.util.PrefUtil;

public class NotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: can extent to implement STOP, PAUSE, RESUME
        switch (intent.getAction()) {
            case AppConstants.ACTION_FINISH:
                AlarmControl.removeAlarm(context);
                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
                // TODO Set timer finished?
                NotificationUtil.showTimerStopped(context, "Finished");
                break;
            case AppConstants.ACTION_CANCEL:
                AlarmControl.removeAlarm(context);
                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
                // TODO Set timer finished?
                NotificationUtil.showTimerStopped(context, "Canceled");
                break;
        }
    }
}
