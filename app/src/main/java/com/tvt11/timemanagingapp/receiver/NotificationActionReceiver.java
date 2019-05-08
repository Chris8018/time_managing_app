package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tvt11.timemanagingapp.activity.MainActivity;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.AlarmControl;
import com.tvt11.timemanagingapp.util.AppConstants;
import com.tvt11.timemanagingapp.util.DateConverter;
import com.tvt11.timemanagingapp.util.NotificationUtil;
import com.tvt11.timemanagingapp.util.PrefUtil;

import java.util.Calendar;
import java.util.Date;

public class NotificationActionReceiver extends BroadcastReceiver {

    private static final String TAG = "NotifActionReceiver";

    private TimerRepository timerRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: can extent to implement STOP, PAUSE, RESUME
        timerRepository = new TimerRepository(context);
        int timerID;

        switch (intent.getAction()) {
            case AppConstants.ACTION_CANCEL:
                AlarmControl.removeAlarm(context);
                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);

                timerID = PrefUtil.getTimerID(context);

                timerRepository.updateTimerRunning(timerID, false);

                NotificationUtil.showTimerStopped(context, "Canceled");
                break;
            case AppConstants.ACTION_FINISH:
                AlarmControl.removeAlarm(context);
                PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);

                timerID = PrefUtil.getTimerID(context);

                Date currentDate = Calendar.getInstance().getTime();

                timerRepository.setTimerFinished(timerID, DateConverter.fromDate(currentDate));

                NotificationUtil.showTimerStopped(context, "Finished");
                break;
        }
    }
}
