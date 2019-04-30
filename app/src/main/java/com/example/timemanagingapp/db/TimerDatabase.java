package com.example.timemanagingapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.timemanagingapp.model.Timer;

@Database(entities = {Timer.class}, version = 1, exportSchema = false)
public abstract class TimerDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
