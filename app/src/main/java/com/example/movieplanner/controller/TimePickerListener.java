package com.example.movieplanner.controller;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class TimePickerListener implements View.OnTouchListener {

    private EditText e2;
    private Context context;
    final Calendar myCalendar = Calendar.getInstance();

    public TimePickerListener(EditText e2,Context context){
        this.e2=e2;
        this.context=context;
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE,minute);
            myCalendar.set(Calendar.SECOND,0);
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
            e2.setText(sdf.format(myCalendar.getTime()));
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                new TimePickerDialog(context, time, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();
        }
        return false;
    }
}
