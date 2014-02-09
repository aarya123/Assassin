package com.AssassinAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Tools.Utilities;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * User: AnubhawArya
 * Date: 2/9/14
 * Time: 9:42 AM
 */
public class KillAsyncTask extends AsyncTask<File, Integer, JSONObject> {

    Context context;

    public KillAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(File... params) {
        if (isCancelled())
            return null;
        try {
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("userid", new StringBody(Utilities.userId));
            entity.addPart("do", new StringBody("kill"));
            entity.addPart("killpic",new FileBody( params[0]));
            HttpPost hp = new HttpPost(Utilities.API_URL + "target.php");
            hp.setEntity(entity);
            HttpResponse hr = new DefaultHttpClient().execute(hp);
            return new JSONObject(EntityUtils.toString(hr.getEntity()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject o) {
        if (o == null) {
            Toast.makeText(context, "Problem getting target!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Toast.makeText(context,o.getString("msg"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(Utilities.TAG, o.toString());
    }
}
