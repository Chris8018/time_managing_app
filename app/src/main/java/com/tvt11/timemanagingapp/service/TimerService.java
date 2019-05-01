package com.tvt11.timemanagingapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.TimeStampConverter;

public class TimerService extends Service {

    private final static String TAG = "TimerService";

    public static final String COUNTDOWN_BR = "timemanagingapp.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    long duration;
    CountDownTimer cdt = null;

    TimerRepository timerRepository;

    TimeStampConverter timeStampConverter = new TimeStampConverter();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        cdt = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };

        cdt.start();
    }

    @Override
    public void onDestroy() {

        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        duration =(long) intent.getExtras().get("duration");
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
