package com.example.timemanagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanagingapp.room_database.TimerInfo;
import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

import static com.example.timemanagingapp.MainActivity.timers;

import com.example.timemanagingapp.converter.TimeStampConverter;

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


//        if (TextUtils.isEmpty(task.getText().toString()))
//            task.setError("The item cannot be empty");

        duration = findViewById(R.id.task_duration);
        desc = findViewById(R.id.task_desc);

        duration.setOnClickListener(view -> {
            TimeStampConverter timeStampConverter = new TimeStampConverter();
            timeDurationPickerDialog = new TimeDurationPickerDialog(
                    CreateTimer.this, (timePicker, time) -> {
                        duration.setText(timeStampConverter.toTimeStamp(time));
                        }, timeStampConverter.fromTimeStamp(duration.getText().toString()));
            timeDurationPickerDialog.show();
        });

        // TODO: Add new timer to database
        button = findViewById(R.id.create_button);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Create new Timer, add it to the end of current list");
            String task_str = task.getText().toString().trim();

            if (!TextUtils.isEmpty(task_str)) {
                timers.add(new TimerInfo(task_str, duration.getText().toString()));
//              timers.add(0, new TimerInfo(task.getText().toString(), duration.getText().toString()));
                startActivity(new Intent(CreateTimer.this, MainActivity.class));
            }

            if (TextUtils.isEmpty(task_str))
                task.setError("The item cannot be empty");
        });

    }
}
