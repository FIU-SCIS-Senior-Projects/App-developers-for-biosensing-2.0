package com.example.biosensing;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ripton on 3/28/2017.
 */

public class DateTimePoint implements DataPointInterface {
    private Date x;
    private double y;

    public DateTimePoint(Date x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x.getTime();
    }

    public Date getDate(){
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        String xString;

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        xString = dateFormat.format(x);

        return "[" + xString + ", " + y + "]";
    }
}
