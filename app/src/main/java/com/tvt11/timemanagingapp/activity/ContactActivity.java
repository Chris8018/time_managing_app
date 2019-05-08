package com.tvt11.timemanagingapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tvt11.timemanagingapp.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        // TODO: need change
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.contact_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
