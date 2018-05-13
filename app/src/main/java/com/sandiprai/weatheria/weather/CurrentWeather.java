package com.sandiprai.weatheria.weather;

import com.sandiprai.weatheria.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sandip on 5/7/2018.
 */

public class CurrentWeather {

    private String locationLabel;
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timezone;

    public CurrentWeather() {
    }

    public CurrentWeather(String locationLabel, String icon,
                          long time, double temperature, double humidity, double precipChance, String summary, String timezone) {
        this.locationLabel = locationLabel;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipChance = precipChance;
        this.summary = summary;
        this.timezone = timezone;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(icon); //Use the getIconId of Forecast class to preserve DRY principle
    }

    public long getTime() {
        return time;
    }

    public String getFormattedTime(){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("h:mm a");

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));

        Date dateTime = new Date(time *1000);
        return simpleDateFormat.format(dateTime);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
