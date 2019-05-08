package com.tvt11.timemanagingapp.activity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
import com.tvt11.timemanagingapp.util.AlarmControl;
import com.tvt11.timemanagingapp.util.DateConverter;
import com.tvt11.timemanagingapp.util.NotificationUtil;
import com.tvt11.timemanagingapp.util.PrefUtil;
import com.tvt11.timemanagingapp.util.TimeConverter;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TimersAdapter.ListItemListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton add_timer_button;

    private TextView current_task_name;
    private TextView current_task_duration;
    private Button cancel_button;
    private Button finish_button;

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
                timerRepository.updateTimerRunning(timerID, false);

                timerState = TimerState.NoTimer;
                updateTimerGUI();
            } else
                Toast.makeText(this, "No timer", Toast.LENGTH_SHORT).show();
        });

        finish_button = findViewById(R.id.finish_button);
        finish_button.setOnClickListener(view -> {
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
//        Log.d(TAG, "onResume is fire");

        initTimer();

        AlarmControl.removeAlarm(this);

        NotificationUtil.hideTimerNotification(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (timerState == TimerState.Running) {
            cdt.cancel();

            long currentTime = Calendar.getInstance().getTimeInMillis();

            long wakeUpTime = AlarmControl.setAlarm(this, currentTime, timeTilFinish);

            NotificationUtil.showTimerRunning(this, wakeUpTime);
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

            long alarmSetUpTime = PrefUtil.getAlarmSetTime(this);
            long currentTime = Calendar.getInstance().getTimeInMillis();

            if (alarmSetUpTime > 0)
                timeTilFinish -= currentTime - alarmSetUpTime;

            startTimer();
        }

        updateTimerGUI();
    }

    private void updateTimerGUI() {
        if (timerState == TimerState.NoTimer) {
            timerName = "No Timer Running";
            timeTilFinish = 0;
        }

        String timeStr = TimeConverter.toTimeStamp(timeTilFinish);

        current_task_name.setText(timerName);
        current_task_duration.setText(timeStr);
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
        timerState = TimerState.NoTimer;

        Date currentDate = Calendar.getInstance().getTime();

        timerRepository.setTimerFinished(timerID, DateConverter.fromDate(currentDate));

        updateTimerGUI();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
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
    public void onCardLongClick(int position) {
        // TODO: start edit activity
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
        if (id == R.id.action_contact) {
            // TODO: go to contact activity
            return true;
        } else if (id == R.id.action_chart) {
            // TODO: go to chart activity
            return true;
        } else if (id == R.id.action_delete_all) {
            // TODO: delete all scheduled timer
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
