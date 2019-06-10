package com.example.movieplanner.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.example.movieplanner.R;
import com.example.movieplanner.datacontrol.SingletonClass;

import java.util.Timer;
import java.util.TimerTask;

public class RemindAgainService extends Service {
    private Integer duration;
    private NotificationManager notificationManager;
    private NotificationChannel channel;
    private NotificationCompat.Builder mBuilder;
    public RemindAgainService() {
    }

    @SuppressWarnings("NewAPI")
    @Override
    public void onCreate(){
        super.onCreate();
        notificationManager = this.getSystemService(NotificationManager.class);
        channel=new NotificationChannel("default_channel_id", "Event", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        mBuilder = new NotificationCompat.Builder(this, "default_channel_id");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Event Notification");
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        try {
            this.duration = Integer.parseInt(prefs.getString("duration", "1"));
        }
        catch (NumberFormatException r){
            this.duration=1;
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        String[] ex=intent.getStringExtra("ex").split("/");
        final Integer noid=Integer.parseInt(ex[1]);
        mBuilder.setContentText("Time to leave for " + SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).getTitle());
        for (int i = 0; i < SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).getNotifications().size(); i++) {
            notificationManager.cancel(SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).getNotifications().get(i));
        }
        SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).setIsdismiss(true);
        Timer timer=new Timer();
        SingletonClass.instance.getEventModel().getmap().getValue().get(ex[0]).setTimer(timer);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationManager.notify(noid, mBuilder.build());
            }
        },duration*60*1000);
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
