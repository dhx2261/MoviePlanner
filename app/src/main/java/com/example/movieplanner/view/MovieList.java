package com.example.movieplanner.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.movieplanner.R;
import com.example.movieplanner.controller.MovieListAdapter;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.viewmodel.EventModel;
import com.example.movieplanner.viewmodel.MovieModel;

import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class MovieList extends AppCompatActivity {
    private MovieModel movieModel;
    private ListView movielist;
    private MovieListAdapter adapter;
    private EventModel eventModel;
    private Activity ac=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        movielist=(ListView)findViewById(R.id.movielayout);
        movieModel=ViewModelProviders.of(this).get(MovieModel.class);
        eventModel= SingletonClass.instance.getEventModel();
        //Set to observe viewmodel
        eventModel.getmap().observe(this,new Observer<Map<String, EventImpl>>(){
            @Override
            public void onChanged(Map<String, EventImpl> i) {
                for(String keys:eventModel.getmap().getValue().keySet()){
                    adapter = new MovieListAdapter(movieModel.getmap().getValue(), ac);
                    //When viewmodel change observed, notify adapter
                    adapter.notifyDataSetChanged();
                    movielist.setAdapter(adapter);
                }
            }
        });
        //Set adapter
        adapter = new MovieListAdapter(movieModel.getmap().getValue(), this);
        adapter.notifyDataSetChanged();
        movielist.setAdapter(adapter);
    }

    @Override
    protected void onRestart()
    {

        eventModel.getmap().observe(this,new Observer<Map<String, EventImpl>>(){
            @Override
            public void onChanged(Map<String, EventImpl> i) {
                for(String keys:eventModel.getmap().getValue().keySet()){
                    movielist.removeAllViews();
                    adapter = new MovieListAdapter(movieModel.getmap().getValue(), ac);
                    adapter.notifyDataSetChanged();
                    movielist.setAdapter(adapter);
                }
            }
        });
        super.onRestart();
        adapter = new MovieListAdapter(movieModel.getmap().getValue(), ac);
        adapter.notifyDataSetChanged();
        movielist.setAdapter(adapter);
    }
}
