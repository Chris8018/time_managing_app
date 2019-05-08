package com.tvt11.timemanagingapp.activity;

import android.app.DatePickerDialog;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tvt11.timemanagingapp.R;
import com.tvt11.timemanagingapp.util.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;

public class ChartActivity extends AppCompatActivity {

    TextView dateView;
    PieChart pieChart;

    private DatePickerDialog datePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.chart_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);

        String currentDateStr = DateConverter.dateValueToString(year, month, dayOfMonth);

        dateView = findViewById(R.id.date_picker);
        dateView.setText(currentDateStr);
        dateView.setOnClickListener(view -> pickDate());

        pieChart = findViewById(R.id.pie_chart);

        // TODO need change
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 10, 10, 10);
        pieChart.setDrawHoleEnabled(false);
        pieChart.animateY(1000, Easing.EaseInCubic);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new PieEntry(945f, "2008"));
        NoOfEmp.add(new PieEntry(1040f, "2009"));
        NoOfEmp.add(new PieEntry(1133f, "2010"));
        NoOfEmp.add(new PieEntry(1240f, "2011"));
        NoOfEmp.add(new PieEntry(1369f, "2013"));
        NoOfEmp.add(new PieEntry(1487f, "2014"));
        NoOfEmp.add(new PieEntry(1501f, "2015"));
        NoOfEmp.add(new PieEntry(1645f, "2016"));
        NoOfEmp.add(new PieEntry(1578f, "2017"));
        NoOfEmp.add(new PieEntry(1695f, "2018"));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Task Name");

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(15f);
    }

    private void pickDate() {
        int[] dateValue = DateConverter.StringToDateValue(dateView.getText().toString());
        int d = dateValue[0];
        int m = dateValue[1];
        int y = dateValue[2];

        datePickerDialog = new DatePickerDialog(
                ChartActivity.this,
                (view, year, month, dayOfMonth) -> {
                    String dateStr = DateConverter.dateValueToString(year, month, dayOfMonth);
                    dateView.setText(dateStr);
                }, y, m ,d
        );
        datePickerDialog.show();
    }
}
