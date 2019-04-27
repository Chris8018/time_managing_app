package com.example.timemanagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanagingapp.room_database.TimerInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FloatingActionButton add_timer_button;

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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TimerAdapter(timers));
        recyclerView.setAdapter(new TimerAdapter());

        add_timer_button = findViewById(R.id.add_timer_button);
        add_timer_button.setOnClickListener(view -> {
            Log.d(TAG, "Add timer button is clicked");
            startActivity(new Intent(MainActivity.this, CreateTimer.class));
        });
    }

}
