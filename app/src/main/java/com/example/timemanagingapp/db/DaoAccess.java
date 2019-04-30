package com.example.timemanagingapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanagingapp.model.Timer;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(Timer timer);


    @Query("SELECT * FROM Timer WHERE finished = 0")
    LiveData<List<Timer>> scheduledTimers();

    @Query("SELECT * FROM Timer WHERE finished = 1")
    LiveData<List<Timer>> finishedTimers();

    @Query("SELECT * FROM Timer")
    LiveData<List<Timer>> allTimers();


//    @Query("SELECT * FROM Timer WHERE id =:taskId")
//    LiveData<Timer> getTask(int taskId);


    @Update
    void updateTask(Timer note);


    @Delete
    void deleteTask(Timer note);

}
