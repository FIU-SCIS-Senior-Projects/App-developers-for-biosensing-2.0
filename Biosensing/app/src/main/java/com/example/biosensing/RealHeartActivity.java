package com.example.biosensing;

import android.os.Bundle;
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
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RealHeartActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private ConnectionClass connectionClass;
    private ArrayList<Integer> rates;
    private ArrayList<Timestamp> times;
    private Calendar rightNow;
    private Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_heart);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.realHeartGraph);

        //series.setColor(Color.GREEN);

        // customize viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(120);
        viewport.setXAxisBoundsManual(true);
        viewport.setScrollable(true);
        viewport.setScalable(true);

        rates = new ArrayList<>();
        times = new ArrayList<>();
        int count = 0;

        //connect to db
        connectionClass = new ConnectionClass();
        con = connectionClass.CONN();

        rightNow = Calendar.getInstance();
        String query = "select time, rate from dbo.heart order by time asc";

        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                times.add(rs.getTimestamp(1));
                rates.add(rs.getInt(2));
                count++;
            }
        }
        catch (SQLException se){
            Log.e("SQLERROR", se.getMessage());
        }

        series = new LineGraphSeries<DataPoint>();
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                long x = (long)dataPoint.getX();
                Date date = new Date(x);
                HeartPoint hp = new HeartPoint(date, dataPoint.getY());

                Toast.makeText(RealHeartActivity.this, "Date/Time, Heart Rate:\n"+hp, Toast.LENGTH_LONG).show();
            }
        });

        for(int i = 0; i < count; i++){
            series.appendData(new DataPoint(times.get(i), rates.get(i)), true, 20);
        }
        graph.addSeries(series);
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this,
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)));
        graph.getGridLabelRenderer().setNumHorizontalLabels(2); // actually shows 1, b/c of space

        // set manual x bounds to have nice steps
        if(!times.isEmpty()){
            viewport.setMinX(times.get(0).getTime());
            viewport.setMaxX(times.get(count-1).getTime());
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        // make real time graph by adding new data and reloading
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    // wait 30 seconds
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        Log.e("THREADINGERROR:", e.getMessage());
                    }
                }
            }
        }).start();

    }
    // add data to graph
    private void addEntry() {

        Timestamp ts = new Timestamp(rightNow.getTimeInMillis());
        int count = 0;
        ArrayList<Integer> rate = new ArrayList<>();
        ArrayList<Timestamp> time = new ArrayList<>();

        //return data after timestamp
        PreparedStatement prep = null;
        String query = "select time, rate from dbo.heart where (time >= ?) order by time asc";
        try{
            prep = con.prepareStatement(query);
            prep.setTimestamp(1, ts);
            ResultSet rs = prep.executeQuery();
            //update timestamp
            rightNow = Calendar.getInstance();

            while(rs.next()){
                time.add(rs.getTimestamp(1));
                rate.add(rs.getInt(2));
                count++;
            }

        }
        catch(SQLException se){
            Log.e("SQLERROR", se.getMessage());
        }

        for(int i = 0; i < count; i++){
            series.appendData(new DataPoint(time.get(i), rate.get(i)), true, 20);
        }
    }

}
