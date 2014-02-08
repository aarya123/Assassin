package com.AssassinAndroid.AsyncTasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Tools.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class RegisterAsyncTask extends AsyncTask<String, Integer, JSONObject> {

	Context context;
    private Exception e;

    public RegisterAsyncTask(Context context) {
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
        String sex = params[2];
        String age = params[3];
        String race = params[4];
        String height = params[5];
        List<NameValuePair> registerParams = new ArrayList<NameValuePair>(6);
    	registerParams.add(new BasicNameValuePair("email_address", email));
    	registerParams.add(new BasicNameValuePair("password", password));
    	registerParams.add(new BasicNameValuePair("sex", sex));
    	registerParams.add(new BasicNameValuePair("race", race));
    	registerParams.add(new BasicNameValuePair("height", height));
    	try {
    		return Utilities.getResponse(Utilities.API_URL + "register.php", registerParams);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		this.e = e;
    		return null;
    	}
    	
    }

    protected void onPostExecute(JSONObject o) {
    	if(o == null) {
    		Toast.makeText(context, "Problem registering!", Toast.LENGTH_SHORT).show();
    	}
    	try {
    		String userId = o.getString("ok");
    		Intent intent = new Intent(context, TargetActivity.class);
    		intent.putExtra("user_id", userId);
    		context.startActivity(intent);
    	}
    	catch(JSONException ex) {
    		ex.printStackTrace();
    		Log.e("RegisterAsyncTask.onPostExecute", o.optString("error"));
    		Toast.makeText(context, "Problem registering! Message " + o.optString("error"), Toast.LENGTH_SHORT).show();
    	}
    }

}
