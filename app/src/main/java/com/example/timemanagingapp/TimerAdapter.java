package com.example.timemanagingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

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
        TimerInfo timer = timers.get(position);
        holder.task_name.setText(timer.getTask());
        holder.duration.setText(timer.getDuration());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TimerHolder extends RecyclerView.ViewHolder {

        private TextView task_name;
        private TextClock duration;
        private TextView delete_button;
        private TextView start_button;

        // TODO: Add click, hold, swipe left, right events for this view holder
        public TimerHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            duration = itemView.findViewById(R.id.duration);
            delete_button = itemView.findViewById(R.id.delete_button);
            start_button = itemView.findViewById(R.id.start_button);

            // TODO: Add click event for button
        }
    }

}
