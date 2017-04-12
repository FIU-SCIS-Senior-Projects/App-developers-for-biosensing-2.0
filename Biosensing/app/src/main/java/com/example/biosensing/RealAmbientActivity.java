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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RealAmbientActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private ConnectionClass connectionClass;
    private ArrayList<Double> temps;
    private ArrayList<Timestamp> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_ambient);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.realAmbientGraph);

        //series.setColor(Color.GREEN);

        // customize viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(-30);
        viewport.setMaxY(110);
        viewport.setXAxisBoundsManual(true);
        viewport.setScrollable(true);
        viewport.setScalable(true);

        temps = new ArrayList<>();
        times = new ArrayList<>();
        int count = 0;

        //connect to db
        connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        String query;
        query = "select time, temp from dbo.tempA order by time asc";

        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                times.add(rs.getTimestamp(1));
                temps.add(rs.getDouble(2));

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
                DateTimePoint dp = new DateTimePoint(date, dataPoint.getY());

                Toast.makeText(RealAmbientActivity.this, "Date/Time, Temperature:\n"+dp, Toast.LENGTH_LONG).show();
            }
        });

        for(int i = 0; i < count; i++){
            series.appendData(new DataPoint(times.get(i), temps.get(i)), true, 20);
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
        // we're going to simulate real time with thread that append data to the graph
        /*new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();*/
    }
    // add data to graph
    private void addEntry() {
        //int y = gen.nextInt(11) + 70;


        // here, we choose to display max 10 points on the viewport and we scroll to end
        //series.appendData(new DataPoint(lastX++, y), true, 10);
    }

}
