package com.sandiprai.weatheria.UI;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sandiprai.weatheria.R;
import com.sandiprai.weatheria.databinding.ActivityMainBinding;
import com.sandiprai.weatheria.weather.CurrentWeather;
import com.sandiprai.weatheria.weather.Day;
import com.sandiprai.weatheria.weather.Forecast;
import com.sandiprai.weatheria.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Forecast forecast;

    private ImageView iconImageView;

    final double latitude = 37.8267;
    final double longitude = -122.4233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(latitude,longitude);
    }

    private void getForecast(double latitude, double longitude) {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        TextView darkSky = (TextView) findViewById(R.id.darkSkyAttribution);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.iconImageView);

        String apiKey = "6c2cb8d31798aa765b985ff33b9c6a0a";


        String forecastURL = "https://api.darksky.net/forecast/" + apiKey + "/"
                + latitude + "," + longitude;

        if (isNetworkAvailable()) {

            //Create OkHttpClient object
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            //Put this request inside a call object
            Call call = client.newCall(request);

            //This call uses asynchronous method so that multiple operations can run concurrently
            call.enqueue(new Callback() {
                //This runs in the event of failure
                @Override
                public void onFailure(Call call, IOException e) {

                }

                //This runs in the event of successful completion
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //Response response = call.execute();
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            forecast = parseForecastDetails(jsonData);
                            CurrentWeather currentWeather = forecast.getCurrentWeather();

                            final CurrentWeather displayWeather = new CurrentWeather(
                                    currentWeather.getLocationLabel(),
                                    currentWeather.getIcon(),
                                    currentWeather.getTime(),
                                    currentWeather.getTemperature(),
                                    currentWeather.getHumidity(),
                                    currentWeather.getPrecipChance(),
                                    currentWeather.getSummary(),
                                    currentWeather.getTimezone()
                            );

                            binding.setWeather(displayWeather);

                            //The background process can't write to UI,
                            // hence this needs to run on UI thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources()
                                            .getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught: ", e);
                    } catch (JSONException e){
                        Log.e(TAG," JSON Exception caught: ", e);
                    }

                }
            });
        }
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        //Create a Forecast object to hold currentweather and arrays of daily and hourly objects
        Forecast forecast = new Forecast();
        forecast.setCurrentWeather(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return  forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        //Get the daily object from the jsonData
        JSONObject daily = forecast.getJSONObject("daily");

        //get the array from the daily object
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];
        for(int i = 0; i < days.length; i++){
            JSONObject jsonDay = data.getJSONObject(i);

            //Create the day object and set its properties
            Day day = new Day();
            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTempMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimeZone(timezone);

            days[i] = day; //Add the day object to the days array
        }

        return days; //return the days array

    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        //Get the hourly object from the jsonData
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data"); //get the array from the hourly object

        //Initialize the hours array with the same size as data array above
        Hour[] hours = new Hour[data.length()];
        for(int i = 0; i < hours.length; i++){
            //get the JSONObject from data array using the index
            JSONObject jsonHour = data.getJSONObject(i);

            //Create the hour object and set its properties
            Hour hour = new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemp(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);

            hours[i] = hour; //Add the hour object to the hours array
        }

        return hours; //return the hours array
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG,"From JSON:" + timezone);

        double latitude = forecast.getDouble("latitude");
        Log.i(TAG, "From JSON: " + latitude);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setLocationLabel("Alcatraz Island, CA");
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimezone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        else {
            Toast.makeText(this, R.string.network_unavailable_msg,Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }

    public void refreshOnClick(View view){
        getForecast(latitude,longitude);
        Toast.makeText(this,"Data Refreshed.", Toast.LENGTH_LONG).show();
    }
    private void alertUserAboutError() {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getFragmentManager(),"error_dialog");
    }

    public void onClickHourlyButton(View view){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        startActivity(intent);
    }
}
