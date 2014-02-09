package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Tools.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/9/14
 * Time: 8:25 AM
 */
public class LocationAsyncTask extends AsyncTask<Location, Integer, JSONObject> {

    Context context;

    public LocationAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(Location... params) {
        if (isCancelled())
            return null;
        Location loc = params[0];
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("user_id", Utilities.userId));
        loginParams.add(new BasicNameValuePair("latitude", loc.getLatitude() + ""));
        loginParams.add(new BasicNameValuePair("longitude", loc.getLongitude() + ""));
        try {
            return Utilities.getResponse(Utilities.API_URL + "location_update.php", loginParams);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(JSONObject o) {
        if (o == null) {
            Toast.makeText(context, "Problem logging in!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(Utilities.TAG, o.toString());
    }
}

