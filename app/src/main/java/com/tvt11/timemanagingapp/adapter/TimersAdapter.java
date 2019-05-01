package com.tvt11.timemanagingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.model.Timer;

import java.util.List;

//import static com.tvt11.timemanagingapp.ui.MainActivity.timers;

public class TimersAdapter extends RecyclerView.Adapter<TimersAdapter.TimerHolder> {

    private static final String TAG = "TimersAdapter";

//    List<Timer> timers;

    private ListItemListener listItemListener;
    private List<Timer> timers;

//    List<Timer> timers;

//    public TimersAdapter() {
//        Log.d(TAG, "Start TimersAdapter without arg");
//    }
//    public TimersAdapter(ListItemListener listItemListener) {
//        Log.d(TAG, "Start TimersAdapter with ListItemListener");
//        this.listItemListener = listItemListener;
//    }

//    public TimersAdapter(List<Timer> timers) {
//        this.timers = timers;
//    }

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
//            Log.d(TAG, holder.task_name.getText() + " start button is pressed");
//            /*
//            if no timer is running -> remove this timer out of recycler view ->
//            show this timer info on top (current timer)
//             */
//            Timer task = timers.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, timers.size());

            listItemListener.onStartClick(position);
        });

        holder.delete_button.setOnClickListener(view -> {
            listItemListener.onDeleteClick(position);
//            Log.d(TAG, holder.task_name.getText() + " delete button is pressed");
//            timers.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, timers.size());
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

    class TimerHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "TimerHolder";

        private TextView task_name;
        private TextView duration;
        private TextView delete_button;
        private TextView start_button;

        // TODO: add hold, swipe left, right events for this view holder
        public TimerHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            duration = itemView.findViewById(R.id.duration);
            delete_button = itemView.findViewById(R.id.delete_button);
            start_button = itemView.findViewById(R.id.start_button);

        }
    }

    public interface ListItemListener {

        void onStartClick(int position);
        void onDeleteClick(int position);
    }

}
