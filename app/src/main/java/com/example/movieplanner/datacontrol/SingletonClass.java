package com.example.movieplanner.datacontrol;

import com.example.movieplanner.viewmodel.EventModel;


/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class SingletonClass{
    private EventModel eventModel;
    public static final SingletonClass instance=new SingletonClass();

    private SingletonClass(){
    }

    public EventModel getEventModel(){
        return eventModel;
    }

    public void setEventModel(EventModel e1){
        this.eventModel=e1;
    }
}
