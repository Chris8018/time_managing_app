package com.tvt11.timemanagingapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.adapter.TimersAdapter;
import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tvt11.timemanagingapp.service.TimerService;
import com.tvt11.timemanagingapp.util.TimeStampConverter;

public class MainActivity extends AppCompatActivity implements TimersAdapter.ListItemListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton add_timer_button;

    private TextView current_task_name;
    private TextView current_task_duration;
    private TextView cancel_button;
    private TextView finish_button;

//    private TimeStampConverter timeStampConverter = new TimeStampConverter();
//    private SharedPreferences sharedPreferences;
//
//    private static final String preferenceName = "countdownTimer";

//    Timer current_task;

    // TODO: global in this class or local to a function
    private RecyclerView recyclerView;
    private TimersAdapter timerAdapter;

    private TimerRepository timerRepository;

    //    public static List<Timer> timers = new ArrayList<>();
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //code
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Replace list with database
        // TODO: Implement adding timer activity
        init();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


//        timers = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            timers.add(new Timer("Task " + i, "00:00:10"));
//        }

        // Initialize posts


//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TimersAdapter(timers));

//        timerAdapter = new TimersAdapter(new TimersAdapter.ListItemListener() {
//            @Override
//            public void onStartClick(int position) {
//                if (current_task == null) {
//                    current_task = timerAdapter.removeAt(position);
//                    TextView current_task_name = findViewById(R.id.current_task_name);
//                    TextView current_task_duration = findViewById(R.id.current_task_duration);
//
//                    current_task_name.setText(current_task.getTaskName());
//                    current_task_duration.setText(current_task.getDuration());
//                    TimeStampConverter timeStampConverter = new TimeStampConverter();
//
//                    new CountDownTimer(timeStampConverter.fromTimeStamp(current_task.getDuration()),
//                            1000) {
//
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            current_task_duration.setText(
//                                    timeStampConverter.toTimeStamp(millisUntilFinished));
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            Toast.makeText(MainActivity.this,
//                                    "Finish Timer", Toast.LENGTH_SHORT).show();
//                            current_task_name.setText("");
//                            current_task_duration.setText("00:00:00");
//                            current_task = null;
//                        }
//                    }.start();
//                } else
//                    Toast.makeText(MainActivity.this,
//                            "A timer is running", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDeleteClick(int position) {
//                Toast.makeText(MainActivity.this,
//                        "Delete " + timers.get(position).getTaskName(), Toast.LENGTH_SHORT).show();
//                timerAdapter.removeAt(position);
//            }
//        });
//        recyclerView.setAdapter(timerAdapter);
//
//        add_timer_button = findViewById(R.id.add_timer_button);
//        add_timer_button.setOnClickListener(view -> {
//            Log.d(TAG, "Add timer button is clicked");
//            startActivity(new Intent(MainActivity.this, CreateTimer.class));
//        });
    }

    private void init() {
//        timeStampConverter = new TimeStampConverter();

        timerRepository = new TimerRepository(getApplicationContext());

//        sharedPreferences = getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: add swipe left and right listener to recycler view holder

        timerRepository.getScheduledTimers().observe(this, timers -> {
            timerAdapter = new TimersAdapter(this, timers);
            recyclerView.setAdapter(timerAdapter);
//            Log.d(TAG, "Timer database size: " + timers.size());
        });

        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(view -> {
            Intent addTimer = new Intent(MainActivity.this, CreateTimer.class);
            startActivity(addTimer);
        });

        current_task_name = findViewById(R.id.current_task_name);
        current_task_duration = findViewById(R.id.current_task_duration);

        cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(view -> {
            // TODO: implement
            /*
            stop service
            set the timer running to false
            set sharedPref cdt running to false
            put the timer back to recycler view.
             */
//            Timer timer = new Timer ("Add this timer to top", "00:10:00");
//            timerRepository.insertTask(timer);
        });

        finish_button = findViewById(R.id.finish_button);
        finish_button.setOnClickListener(view -> {
            // TODO: implement
            /*
            stop service
            set timer db running to false
            set timer sharedPref running to false
            set timer db finished to true
             */
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(br, new IntentFilter(TimerService.COUNTDOWN_BR));
//        Log.i(TAG, "Registered broadcast receiver");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(br);
//        Log.i(TAG, "Unregistered broadcast receiver");
    }

    @Override
    protected void onStop() {
//        try {
//            unregisterReceiver(br);
//        } catch (Exception e) {
//            Log.e(TAG, "Error with TimerService when app stop: " + e);
//        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        stopService(new Intent(this, TimerService.class));
//        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//    private void updateRunningTimer(Intent intent) {
//        // TODO: if intent has countdown and timer is running, dealing with finished timer
//        Log.i(TAG, "Receive time service");
//        long timeTilFinish = intent.getLongExtra("countdown", 0);
//        current_task_duration.setText(timeStampConverter.toTimeStamp(timeTilFinish));
//    }

    @Override
    public void onStartClick(int position) {
        // TODO: implement
        /*
        check if any timer is running
        if no timer is running
        -> start timer
        , change running to true
        , set shared pref time running to true
        , add extra to service to tell how long to countdown
         */

//        if (timerRepository.getNumberOfRunningTimers().getValue() == 0) {
//            Log.d(TAG, "Start timer service");
////            Timer timer = timerAdapter.removeAt(position);
//            Timer timer = timerAdapter.getAt(position);
//            timer.setRunning(true);
//            timerRepository.updateTimer(timer);
//
//            String name = timer.getTaskName();
//            String time = timer.getDuration();
//
////            current_task_name.setText(name);
////            current_task_duration.setText(time);
////
////            Intent timerService = new Intent(this, TimerService.class);
////            // put extra
////            timerService.putExtra("duration", timeStampConverter.fromTimeStamp(time));
////            startService(timerService);
//        } else
//            Log.i(TAG, "A timer is running so another timer cannot be start");
    }

    @Override
    public void onDeleteClick(int position) {
        Log.d(TAG, "Delete task " + timerAdapter.getAt(position).getTaskName());
        timerRepository.deleteTimer(timerAdapter.getAt(position));
        timerAdapter.removeAt(position);
    }
}
