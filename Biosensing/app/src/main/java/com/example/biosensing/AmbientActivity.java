package com.example.biosensing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AmbientActivity extends AppCompatActivity {

    private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    private Calendar now = Calendar.getInstance();
    private Button startButton1;
    private Button startButton2;
    private Date startDate;
    private Date endDate;

    private SlideDateTimeListener listener1 = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            startDate = date;
            Toast.makeText(AmbientActivity.this,
                    dateFormat.format(date), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AmbientActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private SlideDateTimeListener listener2 = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            endDate = date;
            Toast.makeText(AmbientActivity.this,
                    dateFormat.format(date), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AmbientActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient);

        startButton1 = (Button) findViewById(R.id.button4);
        startButton2 = (Button) findViewById(R.id.button5);

        startButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener1)
                        .setInitialDate(now.getTime())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        .setIs24HourTime(false)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });

        startButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener2)
                        .setInitialDate(now.getTime())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        .setIs24HourTime(false)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
    }

    public void go(View view) throws InterruptedException {
        Intent intent = new Intent(this, RangeAmbientActivity.class);
        try{
            intent.putExtra("START_DATE", startDate.getTime());
            intent.putExtra("END_DATE", endDate.getTime());
        }
        catch(NullPointerException n){
            Toast.makeText(AmbientActivity.this, "Please enter both a Start Date and End Date before " +
                    "clicking the Submit button", Toast.LENGTH_LONG).show();
            Thread.sleep(1000);

            return;
        }

        startActivity(intent);

    }

    public void realAmbientTemp(View view){
        Intent intent = new Intent(this, RealAmbientActivity.class);
        startActivity(intent);
    }

}
