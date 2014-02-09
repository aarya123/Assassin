package com.AssassinAndroid.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.AsyncTasks.LocationAsyncTask;
import com.AssassinAndroid.Tools.Utilities;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Date;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 3:12 AM
 */
public class LocationAlarm extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
            setLocationAlarm(context);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        checkGSM(context, intent);
        doWork(context);
        wl.release();
    }

    private void checkGSM(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.i(Utilities.TAG, "Received: " + extras.toString());
                if (extras.getString("msg").equals(Utilities.RADAR)) {
                    Utilities.getSharedPreferences(context).edit().putLong("OPP_" + Utilities.RADAR, new Date().getTime()).commit();
                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(context, LocationAlarm.class);
                    PendingIntent pi = PendingIntent.getBroadcast(context, 1234, i, 0);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pi); //Every second
                } else if (extras.getString("msg").equals(Utilities.INVISIBILITY)) {
                    Utilities.getSharedPreferences(context).edit().putLong("OPP_" + Utilities.RADAR, new Date().getTime()).commit();
                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(context, LocationAlarm.class);
                    PendingIntent pi = PendingIntent.getBroadcast(context, 1234, i, 0);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 1440, pi); // One day
                }
            }
        } else {
            if (Utilities.getSharedPreferences(context).contains("OPP_" + Utilities.RADAR)) {
                if (new Date().getTime() - Utilities.getSharedPreferences(context).getLong("OPP_" + Utilities.RADAR, 0) > 1200000) {
                    Utilities.getSharedPreferences(context).edit().remove("OPP_" + Utilities.RADAR).commit();
                    setLocationAlarm(context);
                }
            }
            if (Utilities.getSharedPreferences(context).contains("OPP_" + Utilities.INVISIBILITY)) {
                if (new Date().getTime() - Utilities.getSharedPreferences(context).getLong("OPP_" + Utilities.INVISIBILITY, 0) > 86400000) {
                    Utilities.getSharedPreferences(context).edit().remove("OPP_" + Utilities.INVISIBILITY).commit();
                    setLocationAlarm(context);
                }
            }
        }
    }

    private void doWork(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null)
                continue;
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
                bestLocation = l;
        }
        if (bestLocation != null)
            new LocationAsyncTask(context).execute(bestLocation);
        else
            Log.d(Utilities.TAG, "Location not found :(");
    }

    public static void setLocationAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1234, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 5, pi); // Millisec * Second * Minute
    }
}
