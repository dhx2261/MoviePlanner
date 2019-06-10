package com.example.movieplanner.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.viewmodel.EventModel;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Haixiao Dai
 * Contains code citied from Android Developer website by Google
 */

public class CheckEventService extends Service {
    private EventModel eventModel;
    private Integer period;
    private Context context;
    private Timer timer;

    public CheckEventService() {

    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onCreate(){
        super.onCreate();
        timer=new Timer();
        context=this;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        eventModel=SingletonClass.instance.getEventModel();
            try {
                period = Integer.parseInt(prefs.getString("period", "1"));
            }
        catch (NumberFormatException r) {
            period = 1;
        }
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(!eventModel.getmap().getValue().isEmpty()) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListen(context), Looper.getMainLooper());
                    }
                }
            }, new Date(), period*60*1000);
        }

    @Override
    public IBinder onBind(Intent intent) {
        Toast toast=Toast.makeText(this,"Service onBind",Toast.LENGTH_SHORT);
        toast.show();
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
