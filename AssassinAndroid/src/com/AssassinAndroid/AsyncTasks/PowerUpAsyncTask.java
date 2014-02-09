package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Tools.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 10:44 AM
 */
public class PowerUpAsyncTask extends AsyncTask<String, Integer, JSONObject> {
    Context context;

    public PowerUpAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(String... params) {
        if (isCancelled())
            return null;
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>(2);
        loginParams.add(new BasicNameValuePair("userid", Utilities.userId));
        loginParams.add(new BasicNameValuePair("powerup", params[0]));
        try {
            return Utilities.getResponse(Utilities.API_URL + "powerup.php", loginParams);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(JSONObject o) {
        if (o == null) {
            Toast.makeText(context, "Problem using powerup!", Toast.LENGTH_SHORT).show();
            //TODO remove from sharedprefs
            return;
        }
        Log.d(Utilities.TAG, o.toString());
    }
}
