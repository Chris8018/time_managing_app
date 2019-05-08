package com.tvt11.timemanagingapp.activity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.model.Timer;
import com.tvt11.timemanagingapp.repo.TimerRepository;
import com.tvt11.timemanagingapp.util.DateConverter;
import com.tvt11.timemanagingapp.util.TimeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = "ChartActivity";

    TextView dateView;
    PieChart pieChart;

    TimerRepository timerRepository;

    private DatePickerDialog datePickerDialog;

    ArrayList<PieEntry> timersData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.chart_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timerRepository = new TimerRepository(getApplicationContext());

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);

        String currentDateStr = DateConverter.dateValueToString(year, month, dayOfMonth);

        dateView = findViewById(R.id.date_picker);
        dateView.setText(currentDateStr);
        dateView.setOnClickListener(view -> pickDate());

        pieChart = findViewById(R.id.pie_chart);
        drawChart();
    }

    private void drawChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 10, 10, 10);
        pieChart.setDrawHoleEnabled(false);
        pieChart.animateY(1000, Easing.EaseInCubic);
        pieChart.getLegend().setEnabled(false);
        pieChart.setTouchEnabled(false);

        timersData = new ArrayList<>();
        try {
            prepareData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieDataSet dataSet = new PieDataSet(timersData, "Task Name");
        PieData data = new PieData(dataSet);

        pieChart.setData(data);

        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(15f);
        dataSet.setValueFormatter(new PercentFormatter(pieChart));
    }

    private void prepareData() throws Exception {
        String dateStr = dateView.getText().toString();
        String dateFormatString = DateConverter.stringToDateFormatString(dateStr);

        List<Timer> finishedTimersByDate = timerRepository.getFinishedTimersByDate(dateFormatString);
        if (finishedTimersByDate.size() == 0)
            timersData.add(new PieEntry(1, "No Task Finish"));
        else {
            AsyncTask<Void, Void, Map<String, Long>> task = new AsyncTask<Void, Void, Map<String, Long>>() {
                @Override
                protected Map<String, Long> doInBackground(Void... voids) {
                    Map<String, Long> map = new HashMap<>();
                    for (Timer timer: finishedTimersByDate) {
                        String key = timer.getTaskName();
                        long value = TimeConverter.fromTimeStamp(timer.getDuration());
                        if (map.containsKey(key)) {
                            map.put(key, map.get(key) + value);
                        } else
                            map.put(key, value);
                    }
                    return map;
                }
            }.execute();
            Map<String, Long> dataMap = task.get();

            for (String key: dataMap.keySet()) {
                timersData.add(new PieEntry(dataMap.get(key), key));
            }
        }
    }

    private void pickDate() {
        int[] dateValue = DateConverter.stringToDateValue(dateView.getText().toString());
        int d = dateValue[0];
        int m = dateValue[1] - 1;
        int y = dateValue[2];

        datePickerDialog = new DatePickerDialog(
                ChartActivity.this,
                (view, year, month, dayOfMonth) -> {
                    String dateStr =
                            DateConverter.dateValueToString(year, month + 1, dayOfMonth);
                    dateView.setText(dateStr);
                    drawChart();
                }, y, m, d
        );
        datePickerDialog.show();
    }
}
