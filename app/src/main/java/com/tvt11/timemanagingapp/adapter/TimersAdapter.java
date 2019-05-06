package com.tvt11.timemanagingapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;

public class TimersAdapter extends RecyclerView.Adapter<TimersAdapter.TimerHolder> {

    private static final String TAG = "TimersAdapter";

    private ListItemListener listItemListener;
    private List<Timer> timers;

    public TimersAdapter(ListItemListener listItemListener, List<Timer> timers) {
        this.listItemListener = listItemListener;
        this.timers = timers;
    }

    @NonNull
    @Override
    public TimersAdapter.TimerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.timer_row, parent, false);
        return new TimerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimersAdapter.TimerHolder holder, int position) {
        Timer timer = timers.get(position);
        holder.task_name.setText(timer.getTaskName());

        holder.duration.setText(timer.getDuration());

        holder.start_button.setOnClickListener(view -> {
            listItemListener.onStartClick(position);
        });

        holder.delete_button.setOnClickListener(view -> {
            listItemListener.onDeleteClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

    public Timer removeAt(int position) {
        Timer task = timers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, timers.size());
        return task;
    }

    public Timer getAt(int position) {
        return timers.get(position);
    }

    public int getIDAt(int position) {
        return getAt(position).getId();
    }

    class TimerHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "TimerHolder";

        private TextView task_name;
        private TextView duration;
        private Button delete_button;
        private Button start_button;

        // TODO: add hold, swipe left, right events for this view holder
        public TimerHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            duration = itemView.findViewById(R.id.duration);
            delete_button = itemView.findViewById(R.id.delete_button);
            start_button = itemView.findViewById(R.id.start_button);

            itemView.setOnLongClickListener(view -> {
                Log.d(TAG, "Long click fire");
                return true;
            });

        }
    }

    public interface ListItemListener {

        void onStartClick(int position);
        void onDeleteClick(int position);
    }

}
