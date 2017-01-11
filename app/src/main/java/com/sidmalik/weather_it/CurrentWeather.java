package com.sidmalik.weather_it;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
