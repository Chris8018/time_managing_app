package com.tvt11.timemanagingapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(Timer timer);


    @Query("SELECT * FROM Timer WHERE finished = 0 AND running = 0")
    LiveData<List<Timer>> observeScheduledTimer();

    @Query("SELECT * FROM Timer WHERE finished = 0 AND running = 0")
    List<Timer> getScheduledTimer();

    @Query("SELECT * FROM Timer WHERE finished = 1")
    List<Timer> finishedTimers();

    @Query("SELECT * FROM Timer WHERE finishedDate = :date AND finished = 1")
    List<Timer> finishedTimersByDate(String date);

    @Query("SELECT * FROM Timer WHERE id = :id")
    Timer getByID(int id);

    @Update
    void updateTimer(Timer timer);


    @Delete
    void deleteTimer(Timer timer);

}
