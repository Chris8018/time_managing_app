package com.tvt11.timemanagingapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.adapter.TimersAdapter;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TimersAdapter.ListItemListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton add_timer_button;

//    Timer current_task;

    // TODO: global in this class or local to a function
    private RecyclerView recyclerView;
    private TimersAdapter timerAdapter;

    private TimerRepository timerRepository;

    // TODO: fix this when start implement database
//    public static List<Timer> timers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // TODO: Replace list with database
        // TODO: Implement adding timer activity

//        timers = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            timers.add(new Timer("Task " + i, "00:00:10"));
//        }

        // Initialize posts

        init();

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
        // TODO: initialize main activity
        timerRepository = new TimerRepository(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: add swipe left and right listener to recycler view holder

        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(view -> {
            Intent addTimer = new Intent(MainActivity.this, CreateTimer.class);
            startActivity(addTimer);
        });

        timerRepository.getScheduledTimers().observe(this, timers -> {
            timerAdapter = new TimersAdapter(this, timers);
            recyclerView.setAdapter(timerAdapter);
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStartClick(int position) {
        // code
    }

    @Override
    public void onDeleteClick(int position) {
        // code
    }

    private void updateGUI() {
        // TODO
    }
}
