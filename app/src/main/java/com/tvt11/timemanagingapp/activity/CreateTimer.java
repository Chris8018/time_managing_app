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

import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

//import static com.tvt11.timemanagingapp.ui.MainActivity.timers;

import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.TimeStampConverter;

public class CreateTimer extends AppCompatActivity {

    private static final String TAG = "CreateTimer";

    private EditText taskName;
    private TextView duration;
    private EditText desc;

    private Button createTimerButton;

    private TimerRepository timerRepository;

    private TimeDurationPickerDialog timeDurationPickerDialog;
//    private TimeStampConverter timeStampConverter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.create_timer_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
//
//        taskName = findViewById(R.id.task_name);
//
//
////        if (TextUtils.isEmpty(taskName.getText().toString()))
////            taskName.setError("The item cannot be empty");
//
//        duration = findViewById(R.id.task_duration);
//        desc = findViewById(R.id.task_desc);
//
//        duration.setOnClickListener(view -> {
//            TimeStampConverter timeStampConverter = new TimeStampConverter();
//            timeDurationPickerDialog = new TimeDurationPickerDialog(
//                    CreateTimer.this, (timePicker, time) -> {
//                        duration.setText(timeStampConverter.toTimeStamp(time));
//                        }, timeStampConverter.fromTimeStamp(duration.getText().toString()));
//            timeDurationPickerDialog.show();
//        });
//
//        createTimerButton = findViewById(R.id.create_button);
//        createTimerButton.setOnClickListener(view -> {
//            Log.d(TAG, "Create new Timer, add it to the end of current list");
//            String task_str = taskName.getText().toString().trim();
//
//            if (!TextUtils.isEmpty(task_str)) {
//                timers.add(new Timer(task_str, duration.getText().toString()));
////              timers.add(0, new Timer(taskName.getText().toString(), duration.getText().toString()));
//                startActivity(new Intent(CreateTimer.this, MainActivity.class));
//            }
//
//            if (TextUtils.isEmpty(task_str))
//                taskName.setError("The item cannot be empty");
//        });

    }

    public void init() {
        timerRepository = new TimerRepository(getApplicationContext());

//        timeStampConverter = new TimeStampConverter();

        taskName = findViewById(R.id.task_name);

        duration = findViewById(R.id.task_duration);
        desc = findViewById(R.id.task_desc);

        duration.setOnClickListener(view -> {
            timeDurationPickerDialog = new TimeDurationPickerDialog(
                    CreateTimer.this, (timePicker, time) -> {
                duration.setText(TimeStampConverter.toTimeStamp(time));
            }, TimeStampConverter.fromTimeStamp(duration.getText().toString()));
            timeDurationPickerDialog.show();
        });

        // TODO: Add new timer to database
        createTimerButton = findViewById(R.id.create_button);
        createTimerButton.setOnClickListener(view -> {
            Log.d(TAG, "Create new Timer, add it to the end of current list");
            String task_str = taskName.getText().toString().trim();

            if (!TextUtils.isEmpty(task_str)) {
                Timer timer = new Timer(task_str, duration.getText().toString());
                timer.setDescription(desc.getText().toString());
                timerRepository.insertTask(timer);
//                timers.add(new Timer(task_str, duration.getText().toString()));
//                timers.add(0, new Timer(taskName.getText().toString(), duration.getText().toString()));
                startActivity(new Intent(CreateTimer.this, MainActivity.class));
            }

            if (TextUtils.isEmpty(task_str))
                taskName.setError("The item cannot be empty");
        });
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
