package com.example.movieplanner.controller;

import android.content.Context;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.viewmodel.EventModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class CalanderClickListener implements AdapterView.OnItemClickListener {
    private String date;
    private Context context;
    private String month;
    private String year;
    private EventModel eventModel;
    private ListView eventbycalander;
    private TextView dateselect;
    private MonthDisplayHelper monthDisplayHelper;
    private int yearint;
    private int monthint;

    public CalanderClickListener(Context context, String month, String year,  ListView eventbycalander,TextView dateselect,int yearint,int monthint){
        this.context=context;
        this.month=month;
        this.year=year;
        this.eventModel= SingletonClass.instance.getEventModel();
        this.eventbycalander=eventbycalander;
        this.dateselect=dateselect;
        this.yearint=yearint;
        this.monthint=monthint;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Get date that user clicked
        monthDisplayHelper=new MonthDisplayHelper(yearint,monthint,7);
        Integer firstday=monthDisplayHelper.getFirstDayOfMonth();
        date=parent.getItemAtPosition(position+3-firstday).toString();
        Map<String, EventImpl> eventlist=new HashMap<String,EventImpl>();
        SimpleDateFormat fm = new SimpleDateFormat("d/MM/yyyy");
        String selecdate=date+"/"+month+"/"+year;
        //Get events on the date user selected and add them to a new arraylist
        for(String keys:eventModel.getmap().getValue().keySet()){
            if(fm.format(eventModel.getmap().getValue().get(keys).getSdate()).toString().equals(selecdate))
                eventlist.put(keys,eventModel.getmap().getValue().get(keys));
        }
        dateselect.setText(date+"/"+month+"/"+year);
        //Use new arrayilst cureated above to set adapter to create eventlist to show on calander view
        eventbycalander.setAdapter(new EventArrayAdapter(context,eventlist));
    }
}
