package com.example.movieplanner.controller;

import android.view.View;
import android.widget.ListView;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.viewmodel.EventModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class DescendingSortListener implements View.OnClickListener {
    StringBuilder sb = new StringBuilder();
    EventModel model1;
    EventArrayAdapter myArrayAdapter;
    ListView el;

    public DescendingSortListener(EventArrayAdapter myArrayAdapter, ListView el) {
        this.model1= SingletonClass.instance.getEventModel();
        this.myArrayAdapter=myArrayAdapter;
        this.el=el;
    }

    @Override
    public void onClick(View v) {
        List<Map.Entry<String, EventImpl>> list = new LinkedList<Map.Entry<String, EventImpl>>(model1.getmap().getValue().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, EventImpl>>() {
            public int compare(Map.Entry<String, EventImpl> o1, Map.Entry<String, EventImpl> o2) {
                if (o2.getValue().getSdate().before(o1.getValue().getSdate())) {
                    return -1;
                } else if (o1.getValue().getSdate().after(o2.getValue().getSdate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Map<String, EventImpl> sortedMap = new LinkedHashMap<String, EventImpl>();
        for (Map.Entry<String, EventImpl> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        model1.getmap().setValue(sortedMap);
        myArrayAdapter.notifyDataSetChanged();
    }



}
