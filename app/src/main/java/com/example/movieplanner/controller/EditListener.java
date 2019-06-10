package com.example.movieplanner.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.view.EditEvent;
import com.example.movieplanner.viewmodel.EventModel;

import java.io.Serializable;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class EditListener implements View.OnClickListener, Serializable {
    private String TAG = getClass().getName();
    private EventModel model1;
    private String Id;
    private Context context;

    public EditListener(Context context, String Id) {
        this.context = context;
        this.Id = Id;
        this.model1=SingletonClass.instance.getEventModel();
    }

    @Override
    public void onClick(View v) {
        //Pass id of event that user want to edit to edit activity
        SingletonClass.instance.setEventModel(model1);
        Intent editItemIntent = new Intent(context, EditEvent.class);
        editItemIntent.putExtra(Intent.EXTRA_TEXT, Id);
        editItemIntent.setType("text/plain");
        if(editItemIntent.resolveActivity(context.getPackageManager()) != null) {
            //Start editevent activity
            context.startActivity(editItemIntent);

        } else {
            Log.i(TAG, "Cannot open activity for this intent");
        }
    }

}


