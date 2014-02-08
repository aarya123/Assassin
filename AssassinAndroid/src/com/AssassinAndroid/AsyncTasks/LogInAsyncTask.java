package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.AssassinAndroid.Tools.Utilities;

/**
 * User: AnubhawArya
 * Date: 2/7/14
 * Time: 10:50 PM
 */
public class LogInAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;

    public LogInAsyncTask(Context context) {
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
