package com.example.timemanagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanagingapp.recycler_view.TimerAdapter;
import com.example.timemanagingapp.room_database.TimerInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FloatingActionButton add_timer_button;

    TimerInfo current_task;

    // TODO: global in this class or local to a function
    RecyclerView recyclerView;
    TimerAdapter timerAdapter;

    // TODO: fix this when start implement database
    public static List<TimerInfo> timers = new ArrayList<>();

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
//            timers.add(new TimerInfo("Task " + i, Integer.toString(i)));
//        }

        // Initialize posts

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TimerAdapter(timers));

        timerAdapter = new TimerAdapter(new TimerAdapter.ListItemListener() {
            @Override
            public void onStartClick(int position) {
                current_task = timerAdapter.removeAt(position);
                TextView current_task_name = findViewById(R.id.current_task_name);
                TextView current_task_duration = findViewById(R.id.current_task_duration);

                current_task_name.setText(current_task.getTask());
                current_task_duration.setText(current_task.getDuration());
            }

            @Override
            public void onDeleteClick(int position) {
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
