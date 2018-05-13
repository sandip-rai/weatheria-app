package com.sandiprai.weatheria.weather;

import com.sandiprai.weatheria.R;

/**
 * Created by inora on 5/11/2018.
 */

public class Forecast {
    private CurrentWeather currentWeather;
    private Hour[] hourlyForecast;
    private Day[] dailyForecast;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Hour[] getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public static int getIconId(String iconString){

        int iconId = R.drawable.clear_day;

        switch (iconString){
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId =R.drawable.snow;
                break;
            case "sleet":
                iconId =R.drawable.sleet;
                break;
            case "wind":
                iconId =R.drawable.wind;
                break;
            case "fog":
                iconId =R.drawable.fog;
                break;
            case "cloudy":
                iconId =R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId =R.drawable.partly_cloudy;
                break;
            case "party-cloudy-night":
                iconId =R.drawable.clear_night;
                break;
        }
        return iconId;
    }
}
