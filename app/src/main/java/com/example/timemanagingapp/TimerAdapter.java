package com.example.timemanagingapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanagingapp.room_database.TimerInfo;

import static com.example.timemanagingapp.MainActivity.timers;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerHolder> {

    private static final String TAG = "TimerAdapter";

//    List<TimerInfo> timers;

    public TimerAdapter() {
        Log.d(TAG, "Start TimerAdapter without arg");
    }

//    public TimerAdapter(List<TimerInfo> timers) {
//        this.timers = timers;
//    }

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
        holder.start_button.setOnClickListener(view -> {
            Log.d(TAG, holder.task_name.getText() + " start button is pressed");
        });
        holder.delete_button.setOnClickListener(view -> {
            Log.d(TAG, holder.task_name.getText() + " delete button is pressed");
            timers.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, timers.size());
        });
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

    class TimerHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "TimerHolder";

        private TextView task_name;
        private TextView duration;
        private TextView delete_button;
        private TextView start_button;

        // TODO: Add click, hold, swipe left, right events for this view holder
        public TimerHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task);
            duration = itemView.findViewById(R.id.duration);
            delete_button = itemView.findViewById(R.id.delete_button);
            start_button = itemView.findViewById(R.id.start_button);

            // TODO: Add click event for button

        }
    }

}
