package com.example.movieplanner.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieplanner.R;
import com.example.movieplanner.model.MovieImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class MovieListAdapter extends BaseAdapter {
    private final ArrayList mData;
    private Activity activity;

    public MovieListAdapter(Map<String, MovieImpl> map, Activity activity) {
        mData = new ArrayList();
        //Convert Map to Arraylist
        mData.addAll(map.entrySet());
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, MovieImpl> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielistitem, parent, false);
        }
        Map.Entry<String, MovieImpl> item = getItem(position);
        //Set view with movie details
        TextView title=(TextView)convertView.findViewById(R.id.movietitle2);
        TextView year=(TextView)convertView.findViewById(R.id.releaseyear2);
        Button select=(Button)convertView.findViewById(R.id.select);
        ImageView poster=(ImageView)convertView.findViewById(R.id.imageView);
        String img=item.getValue().getPoster().toLowerCase();
        String path=img.substring(0,img.indexOf('.'));
        int image=convertView.getResources().getIdentifier(path,"drawable",convertView.getContext().getPackageName());
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imageView);
        imageView.setImageResource(image);
        title.setText(item.getValue().getTitle());
        year.setText(item.getValue().getyearstring());
        select.setOnClickListener(new MovieSelectListener(activity,item.getValue()));
        return convertView;
    }
}
