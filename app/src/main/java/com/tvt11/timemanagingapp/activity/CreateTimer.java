package com.tvt11.timemanagingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.TimeConverter;

import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

public class CreateTimer extends AppCompatActivity {

    private static final String TAG = "CreateTimer";

    private EditText taskName;
    private TextView duration;
    private EditText desc;

    private Button createTimerButton;

    private TimerRepository timerRepository;

    private TimeDurationPickerDialog timeDurationPickerDialog;

    private String task_str;
    private String defaultDuration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.create_timer_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timerRepository = new TimerRepository(getApplicationContext());

        // TODO: make default duration changeable
        defaultDuration = "00:20:00";

        taskName = findViewById(R.id.task_name);

        duration = findViewById(R.id.task_duration);
        duration.setText(defaultDuration);
        duration.setOnClickListener(view -> pickDuration());

        desc = findViewById(R.id.task_desc);

        createTimerButton = findViewById(R.id.create_button);
        createTimerButton.setOnClickListener(view -> {
            Log.d(TAG, "Create new Timer, add it to the end of current list");
            task_str = taskName.getText().toString().trim();

            if (!TextUtils.isEmpty(task_str) &&
                    TimeConverter.fromTimeStamp(duration.getText().toString()) > 0)
                addTimer();

            if (TextUtils.isEmpty(task_str))
                taskName.setError("The item cannot be empty");

            if (TimeConverter.fromTimeStamp(duration.getText().toString()) <= 0)
                duration.setError("Duration should be higher than 0");
        });

    }

    private void pickDuration() {
        timeDurationPickerDialog = new TimeDurationPickerDialog(
                CreateTimer.this,
                (timePicker, time) -> duration.setText(TimeConverter.toTimeStamp(time)),
                TimeConverter.fromTimeStamp(duration.getText().toString())
        );
        timeDurationPickerDialog.show();
    }

    private void addTimer() {
        Timer timer = new Timer(task_str, duration.getText().toString());
        timer.setDescription(desc.getText().toString());
        timerRepository.insertTask(timer);
        startActivity(new Intent(CreateTimer.this, MainActivity.class));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
