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

    public String displayCelsius()
    {
        return "Ambient Temp: " + String.format("%.1f°C", ambient) +
                "\nTarget Temp: " + String.format("%.1f°C", target);
    }

    public String displayFahrenheit()
    {
        return "Ambient Temp: " + String.format("%.1f°F", (ambient * 1.8) + 32) +
                "\nTarget Temp: " + String.format("%.1f°F", (target * 1.8) + 32);
    }

    public double getAmbientInFahrenheit(){
        return ((ambient * 1.8) + 32);
    }

    public double getTargetInFahrenheit(){
        return ((target * 1.8) + 32);
    }
}
