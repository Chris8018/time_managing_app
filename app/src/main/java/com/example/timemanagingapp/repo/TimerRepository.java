package com.example.timemanagingapp.repo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.timemanagingapp.db.TimerDatabase;
import com.example.timemanagingapp.model.Timer;

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

    public void updateTimer() {
        // TODO
    }

    public void deleteTimer() {
        // TODO
    }

    public LiveData<List<Timer>> getScheduledTimers() {
        // TODO
        return null;
    }

    public LiveData<List<Timer>> getTimers() {
        // TODO
        return null;
    }
}
