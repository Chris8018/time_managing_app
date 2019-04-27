package com.example.timemanagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanagingapp.room_database.TimerInfo;
import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

import static com.example.timemanagingapp.MainActivity.timers;

public class CreateTimer extends AppCompatActivity {

    private static final String TAG = "CreateTimer";

    EditText task;
    TextView duration;
    EditText desc;

    Button button;

    TimeDurationPickerDialog timeDurationPickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_timer);

        task = findViewById(R.id.task_name);
        duration = findViewById(R.id.task_duration);
        desc = findViewById(R.id.task_desc);

        duration.setOnClickListener(view -> {
            timeDurationPickerDialog = new TimeDurationPickerDialog(
                    CreateTimer.this, (timePicker, time) -> {
                        int time_in_second = (int) time / 1000;
                        int h = time_in_second / (60 * 60);
                        int m = time_in_second / 60 % 60;
                        int s = time_in_second % 60;
                        duration.setText(h + ":" + m + ":" + s);
                        }, 60000);
            timeDurationPickerDialog.show();
        });
        // TODO: Add new timer to database
        button = findViewById(R.id.create_button);
        button.setOnClickListener(view -> {
            timers.add(new TimerInfo(task.getText().toString(), duration.getText().toString()));
            startActivity(new Intent(CreateTimer.this, MainActivity.class));
        });

    }
}
