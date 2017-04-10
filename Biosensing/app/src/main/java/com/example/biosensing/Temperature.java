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

    //return celsius
    public double getAmbient()
    {
        return ambient;
    }

    public double getTarget()
    {
        return target;
    }

    //return fahrenheit
    public double getAmbientFahr(){
        return ambient * 1.8 + 32;
    }

    public double getTargetFahr(){
        return target * 1.8 + 32;
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

    public Temperature clone(){
        return new Temperature(this.getAmbient(), this.getTarget());
    }
}
