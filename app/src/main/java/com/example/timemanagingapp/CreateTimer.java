package com.example.timemanagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanagingapp.room_database.TimerInfo;

import static com.example.timemanagingapp.MainActivity.timers;

public class CreateTimer extends AppCompatActivity {

    private static final String TAG = "CreateTimer";

    EditText task;
    EditText duration;
    EditText desc;

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_timer);

        task = findViewById(R.id.task_name);
        duration = findViewById(R.id.task_duration);
        desc = findViewById(R.id.task_desc);

        // TODO: Add new timer to database
        button = findViewById(R.id.create_button);
        button.setOnClickListener(view -> {
            timers.add(new TimerInfo(task.getText().toString(), duration.getText().toString()));
            startActivity(new Intent(CreateTimer.this, MainActivity.class));
        });

    }
}
