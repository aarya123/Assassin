package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.AssassinAndroid.Tools.Utilities;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 10:44 AM
 */
public class PowerUpAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;

    public PowerUpAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected String doInBackground(String... params) {
        if (isCancelled())
            return null;
        return "";
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT);
    }
}
