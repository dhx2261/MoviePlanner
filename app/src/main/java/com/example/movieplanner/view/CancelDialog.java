package com.example.movieplanner.view;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieplanner.R;
import com.example.movieplanner.Service.RemindAgainService;
import com.example.movieplanner.datacontrol.SingletonClass;

public class CancelDialog extends Activity {
    private Context context;
    private String eveid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.cancel_dialog);
        Intent intent=getIntent();
        eveid=intent.getStringExtra("eveid");
        TextView title=findViewById(R.id.canceltitle);
        Button yes=findViewById(R.id.yes);
        Button no=findViewById(R.id.no);
        TextView cancelevent = findViewById(R.id.cancelevent);
        cancelevent.setText(SingletonClass.instance.getEventModel().getmap().getValue().get(eveid).getTitle());
        TextView cancelvenue = findViewById(R.id.cancelvenue);
        cancelvenue.setText(SingletonClass.instance.getEventModel().getmap().getValue().get(eveid).getVenue());
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remind=new Intent(context, RemindAgainService.class);
                remind.putExtra("ex",eveid+"/234");
                startService(remind);
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                for (int i = 0; i < SingletonClass.instance.getEventModel().getmap().getValue().get(eveid).getNotifications().size(); i++) {
                    notificationManager.cancel(SingletonClass.instance.getEventModel().getmap().getValue().get(eveid).getNotifications().get(i));
                }
                SingletonClass.instance.getEventModel().getmap().getValue().remove(eveid);
                finish();
            }
        });
    }
}
