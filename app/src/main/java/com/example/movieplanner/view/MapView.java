package com.example.movieplanner.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.movieplanner.R;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.EventImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Date date=new Date();
        // Add a marker in Sydney and move the camera
        LatLng RMIT = new LatLng(-37.809286, 144.9644092);
        ArrayList<EventImpl> sorted=new ArrayList<>();
        for (String key: SingletonClass.instance.getEventModel().getmap().getValue().keySet()) {
            if(SingletonClass.instance.getEventModel().getmap().getValue().get(key).getSdate().getTime()-date.getTime()>=0) {
                sorted.add(SingletonClass.instance.getEventModel().getmap().getValue().get(key));
            }
        }
        Collections.sort(sorted, new Comparator<EventImpl>() {
            @Override
            public int compare(EventImpl o1, EventImpl o2) {
                if (o1.getSdate().before(o2.getSdate())) {
                    return -1;
                } else if (o1.getSdate().after(o2.getSdate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        try {
            for (int i = 0; i < 3; i++) {
                String[] location = sorted.get(i).getLocation().split(",");
                LatLng eventmark = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                mMap.addMarker(new MarkerOptions().position(eventmark).title(sorted.get(i).getTitle()));
            }
        }
        catch (IndexOutOfBoundsException e){

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(RMIT));
    }
}
