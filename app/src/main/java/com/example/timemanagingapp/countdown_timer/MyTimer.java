package com.example.timemanagingapp.countdown_timer;

import android.os.CountDownTimer;

import com.example.timemanagingapp.room_database.TimerInfo;

public class MyTimer extends CountDownTimer {

    public MyTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //change current test
    }

    @Override
    public void onFinish() {
        //save to database
    }


}
