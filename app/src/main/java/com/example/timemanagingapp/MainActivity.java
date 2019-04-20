/**
 * @author Trieu Vi Tran (Chris) - 15800120
 * @version 1.0
 * @date 3/4/2019
 */
package com.example.timemanagingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: Replace list with database
        // TODO: Implement adding timer activity

        List<TimerInfo> timers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            timers.add(new TimerInfo("Task " + i, Integer.toString(i)));
        }

        // Initialize posts

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TimerAdapter(timers));

    }

}
