package com.example.biosensing;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;

import java.text.DateFormat;
import java.util.Date;

public class HeartPoint implements DataPointInterface {
    private Date x;
    private int y;

    public HeartPoint(Date x, int y) {
        this.x = x;
        this.y = y;
    }

    public HeartPoint(Date x, double y) {
        this.x = x;
        this.y = (int)y;
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
        return (double)y;
    }

    @Override
    public String toString() {
        String xString;

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        xString = dateFormat.format(x);

        return "[" + xString + ", " + y + "]";
    }
}
