package com.AssassinAndroid.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Tools.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/9/14
 * Time: 2:15 AM
 */
public class TargetAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    Activity activity;

    public TargetAsyncTask(Activity activity) {
        this.activity = activity;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(activity))
            cancel(true);
    }

    protected JSONObject doInBackground(String... params) {
        if (isCancelled())
            return null;
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("userid", Utilities.userId));
        loginParams.add(new BasicNameValuePair("do", "get"));
        try {
            return Utilities.getResponse(Utilities.API_URL + "target.php", loginParams);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(JSONObject o) {
        if (o == null) {
            Toast.makeText(activity, "Problem getting target!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            TargetActivity.mName.setText(o.getString("name"));
            TargetActivity.mSex.setText(o.getString("sex"));
            TargetActivity.mAge.setText(o.getString("age"));
            TargetActivity.mRace.setText(o.getString("race"));
            TargetActivity.mHeight.setText(o.getString("height")+" inches");
            TargetActivity.mLocation.setText(o.getString("location"));
            JSONArray array = o.getJSONArray("photos");
            for (int i = 0; i < array.length(); i++)
                if (!TargetActivity.imageURLs.contains(array.getString(i)))
                    TargetActivity.imageURLs.add(array.getString(i));
            Utilities.getImageLoader().displayImage(TargetActivity.imageURLs.get(0), TargetActivity.mTargetImage, Utilities.circleOptions);
        } catch (JSONException e) {
            Log.d(Utilities.TAG, o.toString());
            e.printStackTrace();
        }
    }
}
