package com.sandiprai.weatheria.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sandip on 5/11/2018.
 */

public class Day {
    private long time;
    private String summary;
    private double tempMax;
    private String icon;
    private String timeZone;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTempMax() {
        return (int)Math.round(tempMax);
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getIconId(){
        return Forecast.getIconId(icon);
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date(time * 1000);

        return format.format(date);
    }
}
