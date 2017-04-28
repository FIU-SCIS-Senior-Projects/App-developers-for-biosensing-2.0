package com.example.biosensing;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;

public class RangeHeartActivity extends AppCompatActivity {

    //Note: uses both java.sql.Date and java.util.Date

    private Timestamp startDate;
    private Timestamp endDate;
    private ConnectionClass connectionClass;
    private LineGraphSeries<DataPoint> series;
    private ArrayList<Integer> rates;
    private ArrayList<Timestamp> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_heart);

        //get range of dates
        Intent intent = getIntent();
        startDate = new Timestamp(intent.getLongExtra("START_DATE", -1));
        endDate = new Timestamp(intent.getLongExtra("END_DATE", -1));

        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.rangeHeartGraph);

        // customize viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(50);
        //viewport.setMaxY(120);
        viewport.setXAxisBoundsManual(true);
        viewport.setScrollable(true);
        viewport.setScalable(true);

        rates = new ArrayList<>();
        times = new ArrayList<>();
        int count = 0;

        //connect to db
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        PreparedStatement prep = null;
        String query = "select time, rate from dbo.heart where (time >= ?) and (time <= ?) order by time asc";
        try{
            prep = con.prepareStatement(query);
            prep.setTimestamp(1, startDate);
            prep.setTimestamp(2, endDate);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                times.add(rs.getTimestamp(1));
                rates.add(rs.getInt(2));
                count++;
            }

        }
        catch(SQLException se){
            Log.e("SQLERROR", se.getMessage());
        }

        series = new LineGraphSeries<DataPoint>();
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                long x = (long)dataPoint.getX();
                java.util.Date date = new java.util.Date(x);
                HeartPoint hp = new HeartPoint(date, dataPoint.getY());

                Toast.makeText(RangeHeartActivity.this, "Date/Time, Heart Rate:\n"+hp, Toast.LENGTH_LONG).show();
            }
        });

        for(int i = 0; i < count; i++){
            series.appendData(new DataPoint(times.get(i), rates.get(i)), true, 200);
        }
        graph.addSeries(series);
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this,
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)));
        graph.getGridLabelRenderer().setNumHorizontalLabels(2); // actually shows 1, b/c of space

        // set manual x bounds to have nice steps
        if(!times.isEmpty()) {
            viewport.setMinX(times.get(0).getTime());
            viewport.setMaxX(times.get(count - 1).getTime());
        }

        if(!rates.isEmpty()){
            int max = 0;

            for(int i = 0; i < count; i++)
            {
                if(rates.get(i) > max)
                    max = rates.get(i);
            }
            viewport.setMaxY(max + 50);
        }

    }

}
