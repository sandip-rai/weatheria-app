package com.sandiprai.weatheria.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandiprai.weatheria.R;
import com.sandiprai.weatheria.weather.Day;

/**
 * Created by Sandip on 5/12/2018.
 */

public class DayAdapter extends BaseAdapter {

    private Context context;
    private Day[] days;

    public DayAdapter(Context context, Day[] days){
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return days[i];
    }

    @Override
    public long getItemId(int i) {
        return 0; //Not using at all
    }

    //This method is called for everything that is initially displayed
    //in the list, and also each time we scroll a new item onto the list
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null){
            //create new view to set it up
            view = LayoutInflater.from(context).inflate(R.layout.daily_list_item, null);

            //set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
            viewHolder.temperatureLabel = (TextView) view.findViewById(R.id.temperatureLabel);
            viewHolder.dayLabel = (TextView) view.findViewById(R.id.dayNameLabel);

            //Store the holder with the view
            view.setTag(viewHolder);
        }
        else {
            //Use the viewHolder based on the tag
            viewHolder = (ViewHolder) view.getTag();
        }

        Day day = days[i]; //Get the day at the position
        viewHolder.iconImageView.setImageResource(day.getIconId());
        viewHolder.temperatureLabel.setText(day.getTempMax() + "");
        viewHolder.dayLabel.setText(day.getDayOfTheWeek());

        //Return the converted view
        return view;
    }

    private static class ViewHolder{
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
