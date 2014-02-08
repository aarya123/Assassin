package com.AssassinAndroid.Tools;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;

import com.AssassinAndroid.Service.LocationAlarm;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * User: AnubhawArya
 * Date: 2/7/14
 * Time: 10:51 PM
 */
public class Utilities {
    private static ImageLoader mImageLoader;
    private static HttpClient httpClient;

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
        setupHttpClient();
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
    
    private static void setupHttpClient() {
    	httpClient = AndroidHttpClient.newInstance("Assassin Client");
    }
    
    public static JSONObject getResponse(String url, List<NameValuePair> params) throws Exception {
    	HttpPost post = new HttpPost(url);
    	post.setEntity(new UrlEncodedFormEntity(params));
    	HttpResponse response = httpClient.execute(post);
    	String content = new Scanner(new BufferedInputStream(response.getEntity().getContent())).useDelimiter("\\Z").next();
    	JSONObject obj = new JSONObject(content);
    	return obj;
    }
}