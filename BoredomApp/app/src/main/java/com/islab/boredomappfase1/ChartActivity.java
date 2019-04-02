package com.islab.boredomappfase1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        LineChart chart = findViewById(R.id.chart);
        chart.setTouchEnabled(false);
        chart.animateXY(3000, 3000, Easing.EaseOutBack);
        chart.getDescription().setText("BoredDetectionApp");

        List<List<String>> records = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            fis.close();
            isr.close();
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        Bundle extras = getIntent().getExtras();
        String label = "";
        int values = -1;
        if (extras != null) {
            label = extras.getString("posS");
            values = extras.getInt("posI");
        }
        List<Entry> entries = new ArrayList<>();
        try{
            for (int i=0;i<10;i++)
                entries.add(new Entry(i, Integer.parseInt(records.get(i).get(values))));
        }catch (Exception e){
            e.printStackTrace();
        }

        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setColor(Color.argb(255, 255, 213, 4));
        dataSet.setCircleColor(Color.argb(255, 255, 213, 4));
        dataSet.setLineWidth(3f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.argb(150, 255, 213, 4));
        dataSet.setValueTextSize(10f);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}