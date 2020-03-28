package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;


import com.example.login_register.MPAndroidChart.MyFormatter;
import com.example.login_register.Utils.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.List;

public class BarActivity extends BaseActivity {
//    LineChart lineChart;

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        barChart = findViewById(R.id.bar_chart);

        barChart.setDrawBorders(true);
        //设置数据
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new BarEntry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
        String name = "温度";
        BarDataSet barDataSet = new BarDataSet(entries,name);
        BarData data = new BarData(barDataSet);
        barChart.setData(data);

    }




}
