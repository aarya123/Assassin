package com.AssassinAndroid.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                    cacheInMemory(true).cacheOnDisc(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(defaultOptions).build();
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(config);
        }
        return mImageLoader;
    }
}