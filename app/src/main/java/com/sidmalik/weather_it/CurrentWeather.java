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

public class CurrentWeather {
    private static final String TAG = CurrentWeather.class.getSimpleName();
    private String mIcon;
    private long mTime;
    private int mTemp;
    private double mHumidity;
    private  double mPrecip;
    private String mSummary;
    private String mTimezone;


    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        Date dateTime = new Date(getTime() * 1000);
        return formatter.format(dateTime);

    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemp() {
        int celciusTemp = ((mTemp -32)*5)/9;
        return celciusTemp;
    }

    public void setTemp(int temp) {
        mTemp = temp;
    }

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

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getStringObject() {
        return "[ temperature: " + getTemp() + "]";
    }
}
