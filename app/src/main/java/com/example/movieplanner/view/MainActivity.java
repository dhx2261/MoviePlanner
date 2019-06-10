package com.example.movieplanner.view;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.movieplanner.Service.CheckEventService;
import com.example.movieplanner.controller.DescendingSortListener;
import com.example.movieplanner.controller.AscendingSortListener;
import com.example.movieplanner.controller.EventArrayAdapter;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.datacontrol.Settings;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.R;
import com.example.movieplanner.model.MovieImpl;
import com.example.movieplanner.Service.InternetBroadcastReceiver;
import com.example.movieplanner.viewmodel.EventModel;
import com.example.movieplanner.viewmodel.MovieModel;
import com.example.movieplanner.datacontrol.Savetodb;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class MainActivity extends AppCompatActivity implements Serializable {

    private ListView el;
    private EventModel model1;
    EventArrayAdapter myArrayAdapter;
    private MovieModel movieModel;
    private InternetBroadcastReceiver connectivityReceiver;

    //Set action for menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent editItemIntent = new Intent(this, EditEvent.class);
                editItemIntent.putExtra(Intent.EXTRA_TEXT, "-1");
                this.startActivity(editItemIntent);
                return true;
            case R.id.action_calender:
                SingletonClass.instance.setEventModel(model1);
                Intent calanderintent = new Intent(this, CalenderView.class);
                this.startActivity(calanderintent);
                return true;
            case R.id.action_settings:
                Intent settingintent=new Intent(this, Settings.class);
                this.startActivity(settingintent);
                return true;
            case R.id.action_mapview:
                Intent mapintent=new Intent(this, MapView.class);
                this.startActivity(mapintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        model1= ViewModelProviders.of(this).get(EventModel.class);
        model1.getmap().observe(this, new Observer<Map<String, EventImpl>>(){
            @Override
            public void onChanged(Map<String, EventImpl> i) {
                myArrayAdapter=new EventArrayAdapter(getApplicationContext(),model1.getmap().getValue());
                myArrayAdapter.notifyDataSetChanged();
                el=(ListView)findViewById(R.id.eventview);
                el.setAdapter(myArrayAdapter);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (connectivityReceiver == null) {
            connectivityReceiver = new InternetBroadcastReceiver();
        }
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter2);
        movieModel= ViewModelProviders.of(this).get(MovieModel.class);
        movieModel.getmap().observe(
                this,new Observer<Map<String, MovieImpl>>(){
                    @Override
                    public void onChanged(Map<String, MovieImpl> i) {
                    }
                });
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        el=(ListView)findViewById(R.id.eventview);
        myArrayAdapter = new EventArrayAdapter(this,model1.getmap().getValue());
        el.setAdapter(myArrayAdapter);
        myArrayAdapter.notifyDataSetChanged();
        Button sort=(Button)findViewById(R.id.sort);
        sort.setOnClickListener(new AscendingSortListener(myArrayAdapter,el));
        Button decsort=(Button)findViewById(R.id.decsort);
        decsort.setOnClickListener(new DescendingSortListener(myArrayAdapter, el));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myArrayAdapter=new EventArrayAdapter(getApplicationContext(),model1.getmap().getValue());
        myArrayAdapter.notifyDataSetChanged();
        el.setAdapter(myArrayAdapter);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Thread t=new Thread(new Savetodb(getApplicationContext(),model1.getmap().getValue()));
        t.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
        Intent stopser=new Intent(this, CheckEventService.class);
        stopService(stopser);
        NotificationManager notificationManager= this.getSystemService(NotificationManager.class);
        notificationManager.cancelAll();
    }
}
