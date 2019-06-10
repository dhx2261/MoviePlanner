package com.example.movieplanner.controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.viewmodel.EventModel;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class DeleteEventListener implements View.OnClickListener {

    private String Id;
    private Context context;
    EventModel model1;

    public DeleteEventListener(Context context,  String Id) {
        this.model1= SingletonClass.instance.getEventModel();
        this.Id = Id;
        this.context=context;
    }

    @Override
    public void onClick(View v){
        //Remove the event user from dataset
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        for (int i = 0; i < SingletonClass.instance.getEventModel().getmap().getValue().get(Id).getNotifications().size(); i++) {
            notificationManager.cancel(SingletonClass.instance.getEventModel().getmap().getValue().get(Id).getNotifications().get(i));
        }
        if (model1.getmap().getValue().get(Id).getTimer() != null) {
            model1.getmap().getValue().get(Id).getTimer().cancel();
        }
        model1.getmap().getValue().remove(Id);
        Toast toast=Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT);
        toast.show();
        ((Activity)context).finish();
    }
}
