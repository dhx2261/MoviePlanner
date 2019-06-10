package com.example.movieplanner.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.example.movieplanner.R;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.viewmodel.EventModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Haixiao Dai
 */

@SuppressWarnings("NewAPI")
public class MapAPIAsyncTask extends AsyncTask<Void,Void,Boolean> {
    private String API_KEY = "";    //Replace with your own Google Cloud API KEY
    private EventModel eventModel= SingletonClass.instance.getEventModel();
    private Location location;
    private Context context;
    private NotificationManager notificationManager;
    private NotificationChannel channel;
    private Random random;
    private SharedPreferences prefs;
    private Integer duration;
    private Integer threshold;

    public MapAPIAsyncTask(Location location,Context context){
        this.location=location;
        this.context=context;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.notificationManager = context.getSystemService(NotificationManager.class);
        this.random = new Random();
        try {
            this.duration = Integer.parseInt(prefs.getString("duration", "1"));
        }
        catch (NumberFormatException r){
            this.duration=1;
        }
        try {
            this.threshold = Integer.parseInt(prefs.getString("threshold", "60"));
        }
        catch (NumberFormatException r){
            this.threshold=60;
        }
    }

    @Override
    protected Boolean doInBackground(Void...unsed) {
        HttpURLConnection connection = null;
        channel = new NotificationChannel("default_channel_id", "Event", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        try
        {
            for (String key : eventModel.getmap().getValue().keySet()) {
                if (!eventModel.getmap().getValue().get(key).isIsdismiss()) {
                String[] eventlocation = eventModel.getmap().getValue().get(key).getLocation().split(",");
                String urlstring = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + location.getLatitude() + "," + location.getLongitude() + "&destinations=" + eventlocation[0] + "," + eventlocation[1] + "&key=" + API_KEY;
                URL url = new URL(urlstring);
                System.out.println(urlstring);
                connection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = br.readLine()) != null) {
                    sb.append(result + "\n");
                }
                br.close();
                JSONObject distance = new JSONObject(sb.toString());
                JSONArray rows = distance.getJSONArray("rows");
                JSONObject row = rows.getJSONObject(0);
                JSONArray elements = row.getJSONArray("elements");
                JSONObject element = elements.getJSONObject(0);
                JSONObject d = element.getJSONObject("duration");
                Integer sec = d.getInt("value");
                Integer mins = sec / 60;
                Date date=new Date();
                    long diffInMillies = eventModel.getmap().getValue().get(key).getSdate().getTime() - date.getTime();
                    long diftime= TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
                if (diftime>=0&&diftime <= mins+threshold) {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default_channel_id");
                    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                    mBuilder.setContentTitle("Event Notification");
                    Integer notificationid = random.nextInt();
                    Intent intent1 = new Intent(context, DissmissActionService.class);
                    intent1.setAction(Long.toString(System.currentTimeMillis()));
                    String ex = key + "/" + notificationid.toString();
                    intent1.putExtra("startid", ex);
                    PendingIntent dissmisspending = PendingIntent.getService(context, 2, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentText("Time to leave for " + eventModel.getmap().getValue().get(key).getTitle());
                    mBuilder.addAction(R.drawable.ic_notifications_black_24dp, "Dismiss", dissmisspending);
                    Intent intent2=new Intent(context,CancelActionService.class);
                    intent2.setAction(Long.toString(System.currentTimeMillis()));
                    intent2.putExtra("ex",ex);
                    PendingIntent cancelintent=PendingIntent.getService(context,3,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.addAction(R.drawable.ic_notifications_black_24dp, "Cancel", cancelintent);
                    Intent intent3=new Intent(context,RemindAgainService.class);
                    intent3.setAction(Long.toString(System.currentTimeMillis()));
                    intent3.putExtra("ex",ex);
                    PendingIntent pending3=PendingIntent.getService(context,4,intent3,PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.addAction(R.drawable.ic_notifications_black_24dp, "Remind in "+duration.toString()+" minutes", pending3);
                    notificationManager.notify(notificationid, mBuilder.build());
                    eventModel.getmap().getValue().get(key).getNotifications().add(notificationid);
                }
            }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(connection!=null) {
                connection.disconnect();
            }
        }
        return true;
    }
}
