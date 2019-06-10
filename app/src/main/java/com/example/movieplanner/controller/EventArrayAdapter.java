package com.example.movieplanner.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieplanner.R;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.viewmodel.EventModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class EventArrayAdapter extends BaseAdapter {
    private final ArrayList mData;
    private Context context;
    private EventModel model1;

    public EventArrayAdapter(Context context, Map<String, EventImpl> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.context=context;
        this.model1= SingletonClass.instance.getEventModel();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, EventImpl> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlistitem, parent, false);
        }
        SimpleDateFormat fm = new SimpleDateFormat("d/MM/yyyy h:mm:ss a");
        Map.Entry<String, EventImpl> item = getItem(position);
        TextView itemView = (TextView)convertView.findViewById(R.id.item);
        TextView subItemView = (TextView)convertView.findViewById(R.id.sub_item);
        Button edit=(Button)convertView.findViewById(R.id.edit);
        itemView.setText(item.getValue().getTitle());
        TextView showsdate=(TextView)convertView.findViewById(R.id.showstarttime);
        showsdate.setText(fm.format(item.getValue().getSdate()));
        TextView showedate=(TextView)convertView.findViewById(R.id.showendtime);
        showedate.setText(fm.format(item.getValue().getEdate()));
        TextView showlocation=(TextView)convertView.findViewById(R.id.showlocation);
        showlocation.setText(item.getValue().getLocation());
        TextView movietitle=(TextView) convertView.findViewById(R.id.movie);
        TextView attendees=(TextView)convertView.findViewById(R.id.attendees);
        TextView venue=(TextView)convertView.findViewById(R.id.venue);
        venue.setText(item.getValue().getVenue());
        if(item.getValue().getMovie()==null){
            movietitle.setText("No movie added");
        }
        else{
            movietitle.setText(item.getValue().getMovie().getTitle());
        }
        if(item.getValue().getcontacts().size()==0){
            attendees.setText("0");
        }
        else{
            Integer size=item.getValue().getcontacts().size();
            attendees.setText(size.toString());
        }
        edit.setOnClickListener(new EditListener(context,item.getValue().getId()));

        return convertView;
    }
}
