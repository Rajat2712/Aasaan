package com.example.rajat.moswitch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    Spinner spinner;
    LineChart lineChart;
    PieChart pieChart;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        spinner = (Spinner) findViewById(R.id.spinner1);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        barChart = (BarChart) findViewById(R.id.barChart);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        pieChart.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        lineChart.setVisibility(View.VISIBLE);
                        List<Entry> entries = new ArrayList<>();

                        float[] testDataX = new float[]{8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
                        float[] testDataY = new float[]{35, 38, 25, 30, 38, 35, 38, 42, 55, 60, 63, 65, 73};

                        for (int i=0; i<testDataX.length;i++) {
                            entries.add(new Entry(testDataX[i], testDataY[i]));
                        }

                        LineDataSet dataSet = new LineDataSet(entries, "Power consumption in kWh");
                        dataSet.setColor(Color.CYAN);
                        dataSet.setValueTextColor(Color.RED);
                        dataSet.setValueTextSize(14);

                        LineData lineData = new LineData(dataSet);
                        lineChart.setData(lineData);
                        Description description = new Description();
                        description.setText("Today's power consumption");
                        lineChart.setDescription(description);
                        lineChart.invalidate();
                        break;
                    case 1:
                        pieChart.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        lineChart.setVisibility(View.INVISIBLE);
                        List<PieEntry> entries2 = new ArrayList<>();

                        entries2.add(new PieEntry(15f, "Kitchen Bulb",0));
                        entries2.add(new PieEntry(8f, "Bedroom Lamp",1));
                        entries2.add(new PieEntry(25f, "Ceiling Fan",2));
                        entries2.add(new PieEntry(30f, "Geyser",3));
                        entries2.add(new PieEntry(13f, "Bathroom Bulb",4));
                        entries2.add(new PieEntry(21f, "Chimney",5));

                        PieDataSet dataSet2 = new PieDataSet(entries2,"");
                        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                                Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
                        ArrayList<Integer> colors = new ArrayList<Integer>();

                        for(int c: MY_COLORS) colors.add(c);

                        dataSet2.setColors(colors);

                        PieData data = new PieData(dataSet2);
                        data.setValueTextSize(13f);
                        data.setValueFormatter(new PercentFormatter());
                        pieChart.setData(data);
                        pieChart.setDrawHoleEnabled(false);
                        Description description2 = new Description();
                        description2.setText("Household components consumption");
                        pieChart.setDescription(description2);
                        pieChart.animateXY(1400, 1400);
                        break;
                    case 2:
                        pieChart.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.VISIBLE);
                        lineChart.setVisibility(View.INVISIBLE);

                        ArrayList<BarEntry> barEntry = new ArrayList<>();
                        BarDataSet barDataSet = new BarDataSet(barEntry,"Geyser Power Usage");
                        barEntry.add(new BarEntry(25f, 25,"a"));
                        barEntry.add(new BarEntry(20f, 20,"b"));
                        barEntry.add(new BarEntry(18f, 18,"c"));
                        barEntry.add(new BarEntry(5f, 5,"d"));
                        barEntry.add(new BarEntry(4f, 4,"e"));
                        barEntry.add(new BarEntry(1f, 1,"f"));
                        ArrayList<String> labels = new ArrayList<>();
                        labels.add("January");
                        labels.add("February");
                        labels.add("March");
                        labels.add("April");
                        labels.add("May");
                        labels.add("June");
                        BarData data2 = new BarData(barDataSet);
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        barChart.setData(data2);
                        barChart.animateY(5000);
                        barChart.invalidate();
//                        Description desc = new Description();
//                        desc.setText("Geyser Power Usage");
//                        barChart.setDescription(desc);

                        break;
//                    case 3:
//                        break;
                    default:
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
