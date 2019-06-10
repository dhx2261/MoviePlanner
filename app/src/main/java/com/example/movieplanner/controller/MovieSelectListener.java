package com.example.movieplanner.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.movieplanner.model.MovieImpl;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class MovieSelectListener implements View.OnClickListener {

    private Activity activity;
    private MovieImpl movie;

    public MovieSelectListener(Activity activity, MovieImpl movie){
        this.activity=activity;
        this.movie=movie;
    }
    @Override
    public void onClick(View v){
        Intent returnIntent = new Intent();
        //Return movie id user selected to previous activity
        returnIntent.putExtra("result",movie.getId());
        activity.setResult(Activity.RESULT_OK,returnIntent);
        //Finish movie selection
        activity.finish();
    }
}
