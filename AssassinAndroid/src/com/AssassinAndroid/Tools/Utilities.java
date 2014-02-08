package com.AssassinAndroid.Tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.AssassinAndroid.Activities.TargetActivity;
import com.AssassinAndroid.Service.LocationAlarm;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * User: AnubhawArya
 * Date: 2/7/14
 * Time: 10:51 PM
 */
public class Utilities {

	public static final String API_URL = "http://54.201.183.199/";
    private static ImageLoader mImageLoader;

    public static final String PROPERTY_REG_ID = "registration_id";
    static String SENDER_ID = "580453532961";
    public static final String TAG = "AndroidAssassin";
    static GoogleCloudMessaging gcm;
    static String regid;
    public static final String PREFS_NAME = "prefs";
    public static String userId;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static void init(Context context) {
        setupImageLoader(context);
        setupAlarms(context);
        if (checkPlayServices(context)) {
            gcm = GoogleCloudMessaging.getInstance(context);
            regid = getRegistrationId(context);
            if (regid.isEmpty())
                registerInBackground(context);
        } else
            Log.i(TAG, "No valid Google Play Services APK found.");
    }

    private static void setupImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    private static void setupAlarms(Context context) {
        new LocationAlarm().setLocationAlarm(context);
    }
    
    public static JSONObject getResponse(String url, List<NameValuePair> params) throws Exception {
    	HttpClient httpClient = AndroidHttpClient.newInstance("Assassin Client");
    	try {
	    	HttpPost post = new HttpPost(url);
	    	post.setEntity(new UrlEncodedFormEntity(params));
	    	HttpResponse response = httpClient.execute(post);
	    	String content = new Scanner(new BufferedInputStream(response.getEntity().getContent())).useDelimiter("\\Z").next();
	    	Log.i("content", content);
	    	JSONObject obj = new JSONObject(content);
	    	return obj;
    	}
    	finally {
    		httpClient.getConnectionManager().shutdown();
    	}
    }

    public static boolean checkPlayServices(Context context) {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS ? true : false;
    }

    private static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        return registrationId;
    }


    private static SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(TargetActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    private static void registerInBackground(final Context context) {
        new AsyncTask() {
            protected Object doInBackground(Object[] params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    private static void sendRegistrationIdToBackend() {
        //TODO Send to website
    }

    private static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.commit();
    }
    
    public static void startTargetActivity(Context context) {
    	Intent intent = new Intent(context, TargetActivity.class);
		context.startActivity(intent);
		
    }
}