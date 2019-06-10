package com.example.movieplanner.Service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * @author Haixiao Dai
 */

public class LocationListen implements LocationListener {
    private Context context;

    public LocationListen(Context context){
        this.context=context;
    }

    @Override
    public void onLocationChanged(Location location) {
        MapAPIAsyncTask asyncTask=new MapAPIAsyncTask(location,context);
        asyncTask.execute();
        }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
