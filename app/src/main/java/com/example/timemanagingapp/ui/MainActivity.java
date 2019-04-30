package com.example.timemanagingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanagingapp.R;
import com.example.timemanagingapp.adapter.TimersAdapter;
import com.example.timemanagingapp.model.Timer;
import com.example.timemanagingapp.util.TimeStampConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FloatingActionButton add_timer_button;

    Timer current_task;

    // TODO: global in this class or local to a function
    RecyclerView recyclerView;
    TimersAdapter timerAdapter;

    // TODO: fix this when start implement database
    public static List<Timer> timers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: Replace list with database
        // TODO: Implement adding timer activity

//        timers = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            timers.add(new Timer("Task " + i, "00:00:10"));
//        }

        // Initialize posts

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TimersAdapter(timers));

        timerAdapter = new TimersAdapter(new TimersAdapter.ListItemListener() {
            @Override
            public void onStartClick(int position) {
                if (current_task == null) {
                    current_task = timerAdapter.removeAt(position);
                    TextView current_task_name = findViewById(R.id.current_task_name);
                    TextView current_task_duration = findViewById(R.id.current_task_duration);

                    current_task_name.setText(current_task.getTask());
                    current_task_duration.setText(current_task.getDuration());
                    TimeStampConverter timeStampConverter = new TimeStampConverter();

                    // TODO: need some change to redraw when come back from different activity
                    new CountDownTimer(timeStampConverter.fromTimeStamp(current_task.getDuration()),
                            1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            current_task_duration.setText(
                                    timeStampConverter.toTimeStamp(millisUntilFinished));
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(MainActivity.this,
                                    "Finish Timer", Toast.LENGTH_SHORT).show();
                            current_task_name.setText("");
                            current_task_duration.setText("00:00:00");
                            current_task = null;
                        }
                    }.start();
                } else
                    Toast.makeText(MainActivity.this,
                            "A timer is running", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                Toast.makeText(MainActivity.this,
                        "Delete " + timers.get(position).getTask(), Toast.LENGTH_SHORT).show();
                timerAdapter.removeAt(position);
            }
        });
        recyclerView.setAdapter(timerAdapter);

        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(view -> {
            Log.d(TAG, "Add timer button is clicked");
            startActivity(new Intent(MainActivity.this, CreateTimer.class));
        });
    }



}
