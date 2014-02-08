package com.AssassinAndroid.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

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

        doWork(context);

        wl.release();
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
            Toast.makeText(context, "Location is (" + bestLocation.getLatitude() + ", " + bestLocation.getLongitude() + ")", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Location not found :(", Toast.LENGTH_SHORT).show();
    }

    public void setLocationAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1234, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 5, pi); // Millisec * Second * Minute
    }
}
