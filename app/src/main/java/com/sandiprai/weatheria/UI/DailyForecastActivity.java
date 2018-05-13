package com.sandiprai.weatheria.UI;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.sandiprai.weatheria.R;
import com.sandiprai.weatheria.adapters.DayAdapter;
import com.sandiprai.weatheria.weather.Day;

public class DailyForecastActivity extends ListActivity {
    private Day[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
//
//        String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
//                "Friday", "Saturday" };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
//                , android.R.layout.simple_list_item_1,daysOfTheWeek);
//        setListAdapter(adapter);

        DayAdapter adapter = new DayAdapter(this,days);
    }
}
