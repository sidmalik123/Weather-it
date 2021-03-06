package com.sidmalik.weather_it.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sidmalik.weather_it.R;
import com.sidmalik.weather_it.model.CurrentWeather;
import com.sidmalik.weather_it.model.DayWeather;
import com.sidmalik.weather_it.model.HourWeather;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



public class MainActivity extends AppCompatActivity {
    // consants
    private final double DELHI_LAT = 28.6139;
    private final double DELHI_LONG = 77.2090;
    private final String API_KEY = "f774a78633d214b87a8c2832e40dd59e";
    private final String FORECAST_URL = "https://api.darksky.net/forecast/";
    public static final String TAG = MainActivity.class.getSimpleName();
    // other members:
    // 1. model members
    private CurrentWeather mCurrWeather = new CurrentWeather();
    private HourWeather[] mHourlyWeather = null;
    private DayWeather[] mDailyWeather= null;
    // 2. views
    private TextView mTempLabel;
    private TextView mTimeLabel;
    private TextView mSummary;
    private TextView mHumidityLabel;
    private TextView mPrecipLabel;
    private ImageView mRefreshButton;
    private ProgressBar mProgressBar;
    private ImageView mIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeViewConnections();
        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast();
            }
        });

        getForecast();



    }

    private void makeViewConnections() {
        mTempLabel = (TextView) findViewById(R.id.tempLabel);
        mHumidityLabel = (TextView) findViewById(R.id.humidityValue);
        mPrecipLabel = (TextView) findViewById(R.id.precipValue);
        mSummary = (TextView) findViewById(R.id.summaryLabel);
        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
        mRefreshButton = (ImageView) findViewById(R.id.refreshButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mIconView = (ImageView) findViewById(R.id.iconImageView);
    }

    private void updateView() {
        mTempLabel.setText(mCurrWeather.getTemp() + "");
        mTimeLabel.setText(mCurrWeather.getFormattedTime());
        mSummary.setText(mCurrWeather.getSummary());
        mHumidityLabel.setText(mCurrWeather.getHumidity()+"");
        mPrecipLabel.setText(mCurrWeather.getPrecip()+"");
        mIconView.setImageResource(mCurrWeather.getIconId());

    }

    private void updateHourlyWeather(JSONObject jsonResp) throws  JSONException{
        String timezone = jsonResp.getString("timezone");
        JSONArray data = jsonResp.getJSONObject("hourly").getJSONArray("data");
        int len = data.length();
        if(mHourlyWeather == null) mHourlyWeather = new HourWeather[len];

        for(int i = 0; i < len; ++i){
            JSONObject hour = data.getJSONObject(i);
            mHourlyWeather[i] = new HourWeather();
            mHourlyWeather[i].setTemp(hour.getDouble("temperature"));
            mHourlyWeather[i].setSummary(hour.getString("summary"));
            mHourlyWeather[i].setIcon(hour.getString("icon"));
            mHourlyWeather[i].setTime(hour.getLong("time"));
            mHourlyWeather[i].setTimezone(timezone);
        }
    }

    private void updateCurrWeather(JSONObject jsonResp) throws JSONException {
        JSONObject currently = jsonResp.getJSONObject("currently");
        mCurrWeather.setSummary(currently.getString("summary"));
        mCurrWeather.setTime(currently.getLong("time"));
        mCurrWeather.setHumidity(currently.getDouble("humidity"));
        mCurrWeather.setPrecip(currently.getDouble("precipProbability"));
        int temp = (int)currently.getDouble("temperature");
        mCurrWeather.setTemp(temp);
        mCurrWeather.setTimezone(jsonResp.getString("timezone"));
        mCurrWeather.setIcon(currently.getString("icon"));

    }

    private void updateDailyWeather(JSONObject jsonResp) throws JSONException {
        String timezone = jsonResp.getString("timezone");
        JSONArray data = jsonResp.getJSONObject("daily").getJSONArray("data");
        int len = data.length();
        if(mDailyWeather == null) mHourlyWeather = new HourWeather[len];

        for(int i = 0; i < len; ++i){
            JSONObject hour = data.getJSONObject(i);
            mDailyWeather[i] = new DayWeather();
            mDailyWeather[i].setTempMax(hour.getDouble("temperatureMax"));
            mDailyWeather[i].setTempMin(hour.getDouble("temperatureMin"));
            mDailyWeather[i].setSummary(hour.getString("summary"));
            mDailyWeather[i].setIcon(hour.getString("icon"));
            mDailyWeather[i].setTime(hour.getLong("time"));
            mDailyWeather[i].setTimezone(timezone);
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // check both that there is a network and that it is connected
        return networkInfo != null && networkInfo.isConnected();
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private void getForecast(){

        if(isNetworkAvailable()) {
            toggleRefresh();
            String forecastUrl = FORECAST_URL+API_KEY+"/"+DELHI_LAT+","+DELHI_LONG;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            // setting up a JSONObject here
                            String jsonString = response.body().string();
                            JSONObject jsonResp = new JSONObject(jsonString);
                            // set object's weather related fields here
                            updateCurrWeather(jsonResp);
                            updateHourlyWeather(jsonResp);
                            updateDailyWeather(jsonResp);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateView();
                                    toggleRefresh();
                                }
                            });

//                        Log.d("object looks like: ", getStringObject());
                        } else {
                            // throw exception here
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        alertUserAboutError();
                    } catch (JSONException e) {
                        alertUserAboutError();
                    }
                }
            });
        }else{
            alertUserAboutError();
        }

    }



    private void toggleRefresh(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshButton.setVisibility(View.VISIBLE);
        }else{
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshButton.setVisibility(View.INVISIBLE);
        }
    }


}
