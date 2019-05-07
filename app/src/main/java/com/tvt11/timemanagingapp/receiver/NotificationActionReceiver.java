package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tvt11.timemanagingapp.activity.MainActivity;
import com.tvt11.timemanagingapp.util.AlarmControl;
import com.tvt11.timemanagingapp.util.AppConstants;
import com.tvt11.timemanagingapp.util.NotificationUtil;
import com.tvt11.timemanagingapp.util.PrefUtil;

public class NotificationActionReceiver extends BroadcastReceiver {

    private static final String TAG = "NotifActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: can extent to implement STOP, PAUSE, RESUME
        switch (intent.getAction()) {
            case AppConstants.ACTION_CANCEL:
                Log.d(TAG, "Cancel Button on Notif is Clicked");
//                AlarmControl.removeAlarm(context);
//                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
                // TODO Set timer back to scheduled in db
//                NotificationUtil.showTimerStopped(context, "Canceled");
                break;
            case AppConstants.ACTION_FINISH:
                Log.d(TAG, "Finish Button on Notif is Clicked");
//                AlarmControl.removeAlarm(context);
//                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
                // TODO Set timer finished in db
//                NotificationUtil.showTimerStopped(context, "Finished");
                break;
        }
    }
}
