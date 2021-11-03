package com.smhrd.seniorproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

public class LifePattern extends AppCompatActivity {
    private LineChart chart1, chart3;
    private BarChart chart2;
    private Thread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_pattern);

        //커스텀 달력
        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        //

        //1번 linear 차트
//        chart1 = findViewById(R.id.chart1);
//        LineDataSet lineDataset1 = new LineDataSet(dataValues1(), "Data Set 1");
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataset1);
//
//        LineData data = new LineData(dataSets);
//        chart1.setData(data);
//        chart1.invalidate();

        //2번 bar 차트
        chart2 = findViewById(R.id.chart2);
        BarDataSet barDataSet = new BarDataSet(dataValues2(), "Visitors");

        ArrayList<IBarDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(barDataSet);

        BarData data2 = new BarData(dataSets2);
        chart2.setData(data2);
        chart2.invalidate();
        chart2.getDescription().setText("Bar Chart Example");
        chart2.animateY(2000);

        //3번 linear 차트
        chart3 = findViewById(R.id.chart3);
        LineDataSet lineDataset3 = new LineDataSet(dataValues3(), "Data Set 3");

        ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
        dataSets3.add(lineDataset3);

        LineData data3 = new LineData(dataSets3);
        chart3.setData(data3);
        chart3.invalidate();

    }

    //1번 차트
    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVals1 = new ArrayList<>();
        dataVals1.add(new Entry(0, 20));
        dataVals1.add(new Entry(1, 30));
        dataVals1.add(new Entry(2, 40));
        dataVals1.add(new Entry(3, 50));
        dataVals1.add(new Entry(4, 30));

        return dataVals1;

    }
    //2번 차트
    private ArrayList<BarEntry> dataValues2() {
        ArrayList<BarEntry> dataVals2 = new ArrayList<>();
        dataVals2.add(new BarEntry(2014, 420));
        dataVals2.add(new BarEntry(2015, 475));
        dataVals2.add(new BarEntry(2016, 508));
        dataVals2.add(new BarEntry(2017, 660));
        dataVals2.add(new BarEntry(2018, 550));
        dataVals2.add(new BarEntry(2019, 630));
        dataVals2.add(new BarEntry(2020, 470));

        return dataVals2;

    }
    //3번 차트
    private ArrayList<Entry> dataValues3() {
        ArrayList<Entry> dataVals3 = new ArrayList<>();
        dataVals3.add(new Entry(0, 20));
        dataVals3.add(new Entry(10, 302));
        dataVals3.add(new Entry(20, 40));
        dataVals3.add(new Entry(30, 501));
        dataVals3.add(new Entry(40, 300));

        return dataVals3;

    }
}