package com.example.movieplanner.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.movieplanner.datacontrol.SingletonClass;

public class DissmissActionService extends Service {
    public DissmissActionService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        super.onStartCommand(intent,flags,startId);
        String[] ex=intent.getStringExtra("startid").split("/");
        Integer noid=Integer.parseInt(ex[1]);
        SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).setIsdismiss(true);
        for (int i = 0; i < SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).getNotifications().size(); i++) {
            notificationManager.cancel(SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).getNotifications().get(i));
        }
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
