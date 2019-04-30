package com.example.timemanagingapp.model;

public class Timer {
    private String task;
    private String duration;

    public Timer(String task, String duration) {
        this.task = task;
        this.duration = duration;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
