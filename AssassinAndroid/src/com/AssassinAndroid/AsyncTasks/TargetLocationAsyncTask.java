package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Tools.Utilities;
import com.google.android.gms.maps.model.LatLng;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/9/14
 * Time: 10:41 AM
 */
public class TargetLocationAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    Context context;

    public TargetLocationAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(String... params) {
        if (isCancelled())
            return null;
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("userid", Utilities.userId));
        try {
            return Utilities.getResponse(Utilities.API_URL + "location.php", loginParams);
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
        try {
            TargetActivity.mLatLng=new LatLng(o.getDouble("latitude"),o.getDouble("longitude"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(Utilities.TAG, o.toString());
    }
}
