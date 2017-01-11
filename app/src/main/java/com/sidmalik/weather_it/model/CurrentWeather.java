package com.sidmalik.weather_it.model;

public class CurrentWeather extends Weather {
    private static final String TAG = CurrentWeather.class.getSimpleName();
    private double mHumidity;
    private  double mPrecip;



    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecip() {
        return mPrecip;
    }

    public void setPrecip(double precip) {
        mPrecip = precip;
    }

}
