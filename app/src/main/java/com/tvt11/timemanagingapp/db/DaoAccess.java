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
    LiveData<List<Timer>> scheduledTimers();

    @Query("SELECT * FROM Timer WHERE finished = 1")
    LiveData<List<Timer>> finishedTimers();

    @Query("SELECT * FROM Timer")
    LiveData<List<Timer>> allTimers();

    @Query("SELECT * FROM Timer WHERE running = 1 LIMIT 1")
    LiveData<Timer> getRunningTimer();

    @Query("SELECT * FROM Timer WHERE id = :id")
    Timer getByID(int id);

    @Update
    void updateTimer(Timer timer);


    @Delete
    void deleteTimer(Timer timer);

}
