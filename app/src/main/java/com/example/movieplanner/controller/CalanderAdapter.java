package com.example.movieplanner.controller;

import android.util.MonthDisplayHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class CalanderAdapter extends BaseAdapter {

    private MonthDisplayHelper monthDisplayHelper;
    ArrayList<Integer> list;

    public CalanderAdapter(MonthDisplayHelper monthDisplayHelper){
        this.monthDisplayHelper=monthDisplayHelper;
        list=new ArrayList<Integer>();
    }
    @Override
    public int getCount() {
        return monthDisplayHelper.getNumberOfDaysInMonth()+monthDisplayHelper.getFirstDayOfMonth()-1;
    }

    @Override
    public Integer getItem(int position) {
        return list.get(position)-1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        for(int i=0;i<getCount();i++){
            list.add(i);
        }
        TextView cell=new TextView(parent.getContext());
        //Find the frist day in a month
        Integer firstday=monthDisplayHelper.getFirstDayOfMonth();
        //Set view to empty before the first day on the month
        if(position+1<firstday){
            cell.setText("");
        }
        else{
            Integer tem = position+2-firstday;
            cell.setText(tem.toString());
        }

        return cell;
    }
}
