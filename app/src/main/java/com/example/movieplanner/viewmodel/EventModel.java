package com.example.movieplanner.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.datacontrol.JDBC;
import com.example.movieplanner.model.EventImpl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */


public class EventModel extends AndroidViewModel {
    private MutableLiveData<Map<String, EventImpl>> itemsLiveData;
    private Map<String, EventImpl> events = new LinkedHashMap<String, EventImpl>();

    public EventModel(Application application) {
        super(application);
    }

    public MutableLiveData<Map<String, EventImpl>> getmap(){
            if(itemsLiveData == null) {
                itemsLiveData = new MutableLiveData<Map<String, EventImpl>>();
                JDBC jdbc=JDBC.getSingletonInstance(getApplication());
                itemsLiveData.setValue(jdbc.geteventlist());
                SingletonClass.instance.setEventModel(this);

            }
            return itemsLiveData;
    }
}
