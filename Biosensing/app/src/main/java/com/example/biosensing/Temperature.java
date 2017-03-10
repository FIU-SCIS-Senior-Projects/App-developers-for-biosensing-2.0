package com.example.biosensing;

/**
 * Created by Zero on 3/8/2017.
 */

public class Temperature {
    private double ambient;
    private double target;

    public Temperature(double ambient, double target)
    {
        this.ambient = ambient;
        this.target = target;
    }

    public double getAmbient()
    {
        return ambient;
    }

    public double getTarget()
    {
        return target;
    }

    public String toString()
    {
        return "Ambient Temp: " + String.format("%.1f°C", ambient) +
                "\nTarget Temp: " + String.format("%.1f°C", target);
    }
}
