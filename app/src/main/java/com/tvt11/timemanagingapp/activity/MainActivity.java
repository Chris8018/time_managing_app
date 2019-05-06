package com.tvt11.timemanagingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.adapter.TimersAdapter;
import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tvt11.timemanagingapp.util.TimeStampConverter;


public class MainActivity extends AppCompatActivity implements TimersAdapter.ListItemListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton add_timer_button;

    private TextView current_task_name;
    private TextView current_task_duration;
    private Button cancel_button;
    private Button finish_button;

    // TODO: global in this class or local to a function
    private RecyclerView recyclerView;
    private TimersAdapter timerAdapter;

    private TimerRepository timerRepository;

    enum TimerState {
        NoTimer, Running
    }

    private TimerState timerState;
    private CountDownTimer cdt;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_activity);

        timerRepository = new TimerRepository(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        timerRepository.getScheduledTimers().observe(this, timers -> {
            timerAdapter = new TimersAdapter(this, timers);
            recyclerView.setAdapter(timerAdapter);
        });

        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(view -> {
            Intent addTimer = new Intent(MainActivity.this, CreateTimer.class);
            startActivity(addTimer);
        });

        current_task_name = findViewById(R.id.current_name);
        current_task_duration = findViewById(R.id.current_duration);

        cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(view -> {
            // TODO: do sth with shredPref
            if (timerState == TimerState.Running) {
                cdt.cancel();
                timerRepository.updateTimerRunning(id);

                timerState = TimerState.NoTimer;
                updateTimerGUI(0);
            } else
                Toast.makeText(this, "No timer", Toast.LENGTH_SHORT).show();
        });

        finish_button = findViewById(R.id.finish_button);
        finish_button.setOnClickListener(view -> {
            // TODO: do sth with sharedPref
            if (timerState == TimerState.Running) {
                cdt.cancel();
                onTimerFinished();

            } else
                Toast.makeText(this, "No timer", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateTimerGUI(long timeTilFinish) {
        // TODO: save to sharedPref
        if (timerState == TimerState.Running)
            current_task_duration.setText(
                    TimeStampConverter.toTimeStamp(timeTilFinish)
            );
        else {
            current_task_name.setText("No Timer");
            current_task_duration.setText("00:00:00");
        }
    }

    private void onTimerFinished() {
        // TODO: do sth with sharedPref, save date
        timerState = TimerState.NoTimer;
        updateTimerGUI(0);

        timerRepository.setTimerFinished(id);
    }

    @Override
    public void onStartClick(int position) {
        // TODO: start cdt, set cdt on database to running
        if (timerState == null || timerState == TimerState.NoTimer) {
            Timer timer = timerAdapter.getAt(position);
            current_task_name.setText(timer.getTaskName());
            current_task_duration.setText(timer.getDuration());

            id = timer.getId();

            timerRepository.updateTimerRunning(id);

            timerState = TimerState.Running;

            cdt = new CountDownTimer(
                    TimeStampConverter.fromTimeStamp(timer.getDuration()),
                    1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimerGUI(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    onTimerFinished();
                }
            }.start();
        } else
            Toast.makeText(this, "A timer is running", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {
        Log.d(TAG, "Delete task " + timerAdapter.getAt(position).getTaskName());
        timerRepository.deleteTimer(timerAdapter.getAt(position));
    }
}
