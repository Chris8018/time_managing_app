package com.tvt11.timemanagingapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tvt11.timemanagingapp.util.AppConstants;

public class NotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: implement
        switch (intent.getAction()) {
            case AppConstants.ACTION_FINISHED:
                break;
            case AppConstants.ACTION_RUNNING:
                break;
        }
    }
}
