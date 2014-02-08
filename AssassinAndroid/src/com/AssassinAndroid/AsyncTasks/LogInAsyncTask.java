package com.AssassinAndroid.AsyncTasks;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Tools.Utilities;

/**
 * User: AnubhawArya
 * Date: 2/7/14
 * Time: 10:50 PM
 */
public class LogInAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    Context context;
    private Exception e;

    public LogInAsyncTask(Context context) {
        this.context = context;
        e = null;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(String... params) {
        if (isCancelled())
            return null;
        String email = params[0];
        String password = params[1];
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>(2);
    	loginParams.add(new BasicNameValuePair("email_address", email));
    	loginParams.add(new BasicNameValuePair("password", password));
    	try {
    		return Utilities.getResponse(Utilities.API_URL + "login.php", loginParams);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		this.e = e;
    		return null;
    	}
    	
    }

    protected void onPostExecute(JSONObject o) {
    	if(o == null) {
    		Toast.makeText(context, "Problem logging in!", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	try {
    		String userId = o.getString("ok");
    		SharedPreferences settings = context.getSharedPreferences(Utilities.PREFS_NAME, 0);
    		settings.edit().putString("user_id", userId).commit();
    		Utilities.startTargetActivity(context, userId);
    	}
    	catch(JSONException ex) {
    		ex.printStackTrace();
    		Log.e("LoginAsyncTask.onPostExecute", o.optString("error"));
    		Toast.makeText(context, "Problem logging in! Message " + o.optString("error"), Toast.LENGTH_SHORT).show();
    	}
    }
}
