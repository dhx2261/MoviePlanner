package com.example.movieplanner.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.movieplanner.view.CancelDialog;

public class CancelActionService extends Service {
    public CancelActionService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        super.onStartCommand(intent,flags,startId);
        String[] ex=intent.getStringExtra("ex").split("/");
        Integer noid=Integer.parseInt(ex[1]);
        notificationManager.cancel(noid);
        Intent cancel=new Intent(this, CancelDialog.class);
        cancel.putExtra("eveid",ex[0]);
        this.startActivity(cancel);
        this.stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
