package com.example.movieplanner.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.movieplanner.datacontrol.JDBC;
import com.example.movieplanner.model.MovieImpl;

import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class MovieModel extends AndroidViewModel {
    private MutableLiveData<Map<String, MovieImpl>> movielivedata;

    public MovieModel(Application application) {
        super(application);
    }

    public MutableLiveData<Map<String, MovieImpl>> getmap(){
        if(movielivedata == null) {
            movielivedata=new MutableLiveData<Map<String, MovieImpl>>();
            JDBC jdbc=JDBC.getSingletonInstance(getApplication());
            movielivedata.setValue(jdbc.getmovielist());
        }
        return movielivedata;
    }
}
