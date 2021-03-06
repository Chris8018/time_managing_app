package com.tvt11.timemanagingapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Timer {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "finishedDate")
    private String date = "";

    @ColumnInfo(name = "finished")
    private boolean finished = false;

    @ColumnInfo(name = "running")
    private boolean running = false;

    @ColumnInfo(name = "description")
    private String description = "";

//    @Ignore
    public Timer(String taskName, String duration) {
        this.taskName = taskName;
        this.duration = duration;
    }

    @Ignore
    public Timer(
            String taskName,
            String duration,
            String date,
            boolean finished,
            boolean running,
            String description
    ) {
        this.taskName = taskName;
        this.duration = duration;
        this.date = date;
        this.finished = finished;
        this.running = running;
        this.description = description;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
