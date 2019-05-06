package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.tvt11.timemanagingapp.activity.MainActivity;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.DateConverter;
import com.tvt11.timemanagingapp.util.PrefUtil;

import java.util.Calendar;
import java.util.Date;

public class TimerFinishedReceiver extends BroadcastReceiver {

    private TimerRepository timerRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: show notification

        timerRepository = new TimerRepository(context.getApplicationContext());

        final int timerID = PrefUtil.getTimerID(context);

        Date currentDate = Calendar.getInstance().getTime();

        timerRepository.setTimerFinished(timerID, DateConverter.fromDate(currentDate));

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        PrefUtil.setTimerState(MainActivity.TimerState.NoTimer, context);
        PrefUtil.setAlarmSetTime(0, context);
    }
}
