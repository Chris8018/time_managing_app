package com.example.timemanagingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerHolder> {

    private static final String TAG = "TimerAdapter";

    List<TimerInfo> timers;

    public TimerAdapter(List<TimerInfo> timers) {
        this.timers = timers;
    }

    @NonNull
    @Override
    public TimerAdapter.TimerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.timer_row, parent, false);
        return new TimerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerAdapter.TimerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TimerHolder extends RecyclerView.ViewHolder {
        public TimerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
