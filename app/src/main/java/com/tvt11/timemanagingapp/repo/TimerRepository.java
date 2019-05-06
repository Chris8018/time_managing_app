package com.tvt11.timemanagingapp.repo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.tvt11.timemanagingapp.db.TimerDatabase;
import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TimerRepository {

    private static final String DB_NAME = "db_timer";

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
        // TODO: implement Async
        return timerDatabase.daoAccess().scheduledTimers();
    }

    public LiveData<List<Timer>> getFinishedTimers() {
        // TODO: implement Async
        return timerDatabase.daoAccess().finishedTimers();
    }

    public LiveData<Timer> getRunningTimer() {
        // TODO: implement Async
        return timerDatabase.daoAccess().getRunningTimer();
    }

    public Timer getByID(int id) throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, Timer> task = new AsyncTask<Void, Void, Timer>() {
            @Override
            protected Timer doInBackground(Void... voids) {
                return timerDatabase.daoAccess().getByID(id);
            }
        }.execute();
        return task.get();
    }

    public void updateTimerRunning(int id) {
        new Thread(() -> {
            try {
                Timer timer = getByID(id);
                timer.setRunning(!timer.getRunning());
                updateTimer(timer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void updateTimerRunning(int id, boolean running) {
        new Thread(() -> {
            try {
                Timer timer = getByID(id);
                timer.setRunning(running);
                updateTimer(timer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }


    public void setTimerFinished(int id) {
        new Thread(() -> {
            try {
                Timer timer = getByID(id);
                timer.setRunning(false);
                timer.setFinished(true);
                updateTimer(timer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
