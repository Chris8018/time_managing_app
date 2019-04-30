package com.example.timemanagingapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Timer {

//    @PrimaryKey(autoGenerate = true)
//    private int id;

    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "finished")
    private Boolean finished = false;

    private String description;

    public Timer(String task, String duration) {
        this.taskName = task;
        this.duration = duration;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
