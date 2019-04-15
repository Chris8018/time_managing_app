package com.example.timemanagingapp;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerHolder> {

    public TimerAdapter() {
    }

    @NonNull
    @Override
    public TimerAdapter.TimerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
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
