package com.tvt11.timemanagingapp.repo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.tvt11.timemanagingapp.db.TimerDatabase;
import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;

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

    public List<Timer> getAllTimers() {
        try {
            AsyncTask<Void, Void, List<Timer>> task = new AsyncTask<Void, Void, List<Timer>>() {
                @Override
                protected List<Timer> doInBackground(Void... voids) {
                    return timerDatabase.daoAccess().getAllTimers();
                }
            }.execute();
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LiveData<List<Timer>> observeScheduledTimer() {
        return timerDatabase.daoAccess().observeScheduledTimer();
    }

    public List<Timer> getScheduledTimers() {
        try {
            AsyncTask<Void, Void, List<Timer>> task = new AsyncTask<Void, Void, List<Timer>>() {
                @Override
                protected List<Timer> doInBackground(Void... voids) {
                    return timerDatabase.daoAccess().getScheduledTimer();
                }
            }.execute();
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteScheduledTimers() {
        new Thread(() -> {
            List<Timer> timers = timerDatabase.daoAccess().getScheduledTimer();
            for(Timer timer: timers)
                deleteTimer(timer);
        }).start();
    }

    public List<Timer> getFinishedTimers() {
        try {
            AsyncTask<Void, Void, List<Timer>> task = new AsyncTask<Void, Void, List<Timer>>() {
                @Override
                protected List<Timer> doInBackground(Void... voids) {
                    return timerDatabase.daoAccess().finishedTimers();
                }
            }.execute();
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Timer> getFinishedTimersByDate(String date) {
        try {
            AsyncTask<Void, Void, List<Timer>> task = new AsyncTask<Void, Void, List<Timer>>() {
                @Override
                protected List<Timer> doInBackground(Void... voids) {
                    return timerDatabase.daoAccess().finishedTimersByDate(date);
                }
            }.execute();
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Timer getByID(int id) {
        try {
            AsyncTask<Void, Void, Timer> task = new AsyncTask<Void, Void, Timer>() {
                @Override
                protected Timer doInBackground(Void... voids) {
                    return timerDatabase.daoAccess().getByID(id);
                }
            }.execute();
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateTimerRunning(int id) {
        new Thread(() -> {
            Timer timer = getByID(id);
            timer.setRunning(!timer.getRunning());
            updateTimer(timer);
        }).start();
    }

    public void updateTimerRunning(int id, boolean running) {
        new Thread(() -> {
            Timer timer = getByID(id);
            timer.setRunning(running);
            updateTimer(timer);
        }).start();
    }


    public void setTimerFinished(int id) {
        new Thread(() -> {
            Timer timer = getByID(id);
            timer.setRunning(false);
            timer.setFinished(true);
            updateTimer(timer);
        }).start();
    }

    public void setTimerFinished(int id, String date) {
        new Thread(() -> {
            Timer timer = getByID(id);
            timer.setRunning(false);
            timer.setFinished(true);
            timer.setDate(date);
            updateTimer(timer);
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
