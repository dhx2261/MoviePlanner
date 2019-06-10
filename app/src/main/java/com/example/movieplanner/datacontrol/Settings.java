package com.example.movieplanner.datacontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movieplanner.R;
import com.example.movieplanner.Service.CheckEventService;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 2
 */

@SuppressWarnings("deprecation")
public class Settings extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private  SharedPreferences pref;
    private EditTextPreference threshold;
    private EditTextPreference duration;
    private EditTextPreference period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        threshold=(EditTextPreference)getPreferenceScreen().findPreference("threshold");
        duration=(EditTextPreference)getPreferenceScreen().findPreference("duration");
        period=(EditTextPreference)getPreferenceScreen().findPreference("period");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Toast.makeText(this, key+" changed to: "
                    + sharedPreferences.getString(key,"1"), Toast.LENGTH_SHORT).show();
            Intent stopsintent=new Intent(this, CheckEventService.class);
            stopService(stopsintent);
            Intent startintent=new Intent(this, CheckEventService.class);
            startService(startintent);
    }
}
