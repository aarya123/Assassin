package com.AssassinAndroid.AsyncTasks;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Environment;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Tools.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class RegisterAsyncTask extends AsyncTask<Object, Integer, JSONObject> {

    Context context;

    public RegisterAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        if (!Utilities.isNetworkAvailable(context))
            cancel(true);
    }

    protected JSONObject doInBackground(Object... params) {
        if (isCancelled())
            return null;
        try {
            MultipartEntity entity = new MultipartEntity();

            String email = (String) params[0];
            String password = (String) params[1];
            String sex = (String) params[2];
            String age = (String) params[3];
            String race = (String) params[4];
            String height = (String) params[5];
            Bitmap bmp1 = (Bitmap) params[6];
            Bitmap bmp2 = (Bitmap) params[7];
            Bitmap bmp3 = (Bitmap) params[8];
            String location=(String) params[9];
            entity.addPart("email_address", new StringBody(email));
            entity.addPart("password", new StringBody(password));
            entity.addPart("sex", new StringBody(sex));
            entity.addPart("age", new StringBody(age));
            entity.addPart("race", new StringBody(race));
            entity.addPart("height", new StringBody(height));
            entity.addPart("locations", new StringBody(location));
            entity.addPart("train_files", new FileBody(bmpToFile(bmp1)));
            entity.addPart("train_files", new FileBody(bmpToFile(bmp2)));
            entity.addPart("train_files", new FileBody(bmpToFile(bmp3)));
            HttpPost hp = new HttpPost(Utilities.API_URL + "register.php");
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

    private File bmpToFile(Bitmap bmp) {
        try {
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Assassin";
            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, "assassin" + Math.floor(Math.random() * 100) + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 0, fOut);
            fOut.flush();
            fOut.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject o) {
        if (o == null) {
            Toast.makeText(context, "Problem registering!", Toast.LENGTH_SHORT).show();
        }
        try {
            Log.d(Utilities.TAG, o.toString());
            String userId = o.getString("ok");
            Intent intent = new Intent(context, TargetActivity.class);
            intent.putExtra("user_id", userId);
            context.startActivity(intent);
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.e("RegisterAsyncTask.onPostExecute", o.optString("error"));
            Toast.makeText(context, "Problem registering! Message " + o.optString("error"), Toast.LENGTH_SHORT).show();
        }
    }

}
