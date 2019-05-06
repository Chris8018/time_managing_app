package com.tvt11.timemanagingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.tvt11.timemanagingapp.util.PrefUtil;
import com.tvt11.timemanagingapp.util.TimeConverter;


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

    public enum TimerState {
        NoTimer, Running
    }

    private TimerState timerState = TimerState.NoTimer;
    private CountDownTimer cdt;

    private int timerID;
    private String timerName;
    private long timeTilFinish;

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
            if (timerState == TimerState.Running) {
                cdt.cancel();
                timerRepository.updateTimerRunning(timerID);

                timerState = TimerState.NoTimer;
                updateTimerGUI();
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
        Log.d(TAG, "onResume is fire");

        initTimer();

        // TODO: remove alarm
        // TODO: hide notification
//
//        removeAlarm(this);
//
//        Noti - hide timer noti
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (timerState == TimerState.Running) {
            cdt.cancel();

            // TODO: set alarm
        }

        PrefUtil.setTimerId(timerID, this);
        PrefUtil.setTimerName(timerName, this);
        PrefUtil.setTimeRemain(timeTilFinish, this);
        PrefUtil.setTimerState(timerState, this);
    }

    private void initTimer() {
        timerState = PrefUtil.getTimerState(this);

        if (timerState == TimerState.Running) {
            timerID = PrefUtil.getTimerID(this);
            timeTilFinish = PrefUtil.getTimeRemain(this);
            timerName = PrefUtil.getTimerName(this);
            startTimer();
        }

        updateTimerGUI();
    }

    private void updateTimerGUI() {
        if (timerState == TimerState.NoTimer) {
            timerName = "No Timer";
            timeTilFinish = 0;
        }

        String timeStr = TimeConverter.toTimeStamp(timeTilFinish);

        current_task_name.setText(timerName);
        current_task_duration.setText(timeStr);

//        if (timerState == TimerState.Running)
//            current_task_duration.setText(
//                    TimeConverter.toTimeStamp(timeTilFinish)
//            );
//        else {
//            current_task_name.setText("No Timer");
//            current_task_duration.setText("00:00:00");
//        }
    }

    private void startTimer() {
        updateTimerGUI();

        cdt = new CountDownTimer(timeTilFinish, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeTilFinish = millisUntilFinished;
                updateTimerGUI();
            }

            @Override
            public void onFinish() {
                onTimerFinished();
            }
        }.start();


    }

    private void onTimerFinished() {
        // TODO: do sth with sharedPref, save date
        timerState = TimerState.NoTimer;

        timerRepository.setTimerFinished(timerID);

        updateTimerGUI();
    }

    @Override
    public void onStartClick(int position) {
        if (timerState == TimerState.NoTimer) {
            Timer timer = timerAdapter.getAt(position);
            timerID = timer.getId();
            timerName = timer.getTaskName();
            timerState = TimerState.Running;
            timeTilFinish = TimeConverter.fromTimeStamp(timer.getDuration());

            timer.setRunning(true);
            timerRepository.updateTimer(timer);

            startTimer();
        } else
            Toast.makeText(this, "A timer is running", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Log.d(TAG, "Delete task " + timerAdapter.getAt(position).getTaskName());
        timerRepository.deleteTimer(timerAdapter.getAt(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
