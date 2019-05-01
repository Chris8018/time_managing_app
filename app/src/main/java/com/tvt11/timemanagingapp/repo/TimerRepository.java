package com.tvt11.timemanagingapp.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.tvt11.timemanagingapp.db.TimerDatabase;
import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;

public class TimerRepository {

    private String DB_NAME = "dp_timer";

    private TimerDatabase timerDatabase;

    public TimerRepository(Context context) {
        timerDatabase = Room.databaseBuilder(context, TimerDatabase.class, DB_NAME).build();
    }

    public void insertTask(String taskName, String duration) {
        insertTask(new Timer(taskName, duration));
    }

    public void insertTask(final Timer timer) {
        new Thread(() -> timerDatabase.daoAccess().insertTask(timer)).start();
    }

    public LiveData<List<Timer>> getScheduledTimers() {
        return timerDatabase.daoAccess().scheduledTimers();
    }

    public LiveData<List<Timer>> getFinishedTimers() {
        return timerDatabase.daoAccess().finishedTimers();
    }

    public LiveData<Timer> getRunningTimer() {
        return timerDatabase.daoAccess().getRunningTimer();
    }

    public void updateTimer(final Timer timer) {
        new Thread(() -> timerDatabase.daoAccess().updateTimer(timer)).start();
    }

    public void deleteTimer(final Timer timer) {
        new Thread(() -> timerDatabase.daoAccess().deleteTimer(timer)).start();
    }

    public void nukeTable() {
        new Thread(() -> timerDatabase.clearAllTables()).start();
    }
}
