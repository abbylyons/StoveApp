package com.example.abby.stovetemperatureapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.opencsv.CSVReader;

import android.util.Log;

import android.graphics.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;






public class ViewTempData extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_temp_data);

        LineChart chart = new LineChart(getApplicationContext());

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();


        InputStream inputStream = getResources().openRawResource(R.raw.testlist);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> scoreList = csvFile.read();

        //First index indicates what row (as many rows as data points)
        //Second index indicates time (0) versus temp (1)

        int lengthOfArray = scoreList.size();

        //set y-values
        for(int i=0; i<lengthOfArray-1; i++){
            Entry c1 = new Entry(Float.parseFloat(scoreList.get(i)[1]),i);
            valsComp1.add(c1);

        }


        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setColor(Color.parseColor("#0099CC"));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        ArrayList<String> xVals = new ArrayList<String>();

        //set x-values
        for(int i=0; i<lengthOfArray-1; i++){
            xVals.add(scoreList.get(i)[0]);
        }

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate();
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);


        setContentView(chart);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_temp_data, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
