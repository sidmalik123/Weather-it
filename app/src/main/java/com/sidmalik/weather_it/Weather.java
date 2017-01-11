package com.sidmalik.weather_it;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Weather {
    private long mTime;
    private String mIcon;
    private double mTemp;
    private String mSummary;
    private String mTimezone;
    private double mTempMax;
    private double mTempMin;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }



    public void setTemp(double temp) {
        mTemp = temp;
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

    public double getTempMax() {
        return mTempMax;
    }

    public void setTempMax(double tempMax) {
        mTempMax = tempMax;
    }

    public double getTempMin() {
        return mTempMin;
    }

    public void setTempMin(double tempMin) {
        mTempMin = tempMin;
    }

    // methods with added functionalities
    //
    public int getIconId(){
        int iconId = -1;
        if(mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }



    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        Date dateTime = new Date(getTime() * 1000);
        return formatter.format(dateTime);

    }


    // no default getTemp, temp is always returned in celius
    public int getTemp() {
        int celciusTemp = (int) ((mTemp -32)*5)/9;
        return celciusTemp;
    }
}
