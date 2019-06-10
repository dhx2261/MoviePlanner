package com.example.movieplanner.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 2
 * Contains code citited from Android Developer website by Google
 */

public class InternetBroadcastReceiver extends BroadcastReceiver {
    CheckEventService alarm;

    public InternetBroadcastReceiver(){
        alarm=new CheckEventService();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                context.startService(new Intent(context,CheckEventService.class));

            } else {
                context.stopService(new Intent(context,CheckEventService.class));
            }
        }
    }
