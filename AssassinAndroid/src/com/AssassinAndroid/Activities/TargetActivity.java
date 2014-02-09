package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.AssassinAndroid.AsyncTasks.PowerUpAsyncTask;
import com.AssassinAndroid.Tools.CircleBitmapDisplayer;
import com.AssassinAndroid.Tools.Utilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 12:12 AM
 */
public class TargetActivity extends Activity {

    ImageView mTargetImage, mRadar, mInvisibility;
    TextView mName, mSex, mRace, mHeight, mAge, mLocation;
    Button mKill;
    GoogleMap map;
    Bitmap mKillBitmap;
    private static final int CAMERA_REQUEST = 1888;
    View.OnClickListener mPowerUpOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            v.setVisibility(View.GONE);
            if (v == mRadar)
                new PowerUpAsyncTask(TargetActivity.this).execute("0");
            else if (v == mInvisibility)
                new PowerUpAsyncTask(TargetActivity.this).execute("1");
        }
    };
    View.OnClickListener mKillOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    };
    LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            Log.d(Utilities.TAG, "(" + location.getLatitude() + ", " + location.getLongitude() + ")");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target);
        mTargetImage = (ImageView) findViewById(R.id.mTargetImage);
        mName = (TextView) findViewById(R.id.mName);
        mSex = (TextView) findViewById(R.id.mSex);
        mRace = (TextView) findViewById(R.id.mRace);
        mHeight = (TextView) findViewById(R.id.mHeight);
        mAge = (TextView) findViewById(R.id.mAge);
        mLocation = (TextView) findViewById(R.id.mLocation);
        mKill = (Button) findViewById(R.id.mKill);
        mRadar = (ImageView) findViewById(R.id.mRadar);
        mInvisibility = (ImageView) findViewById(R.id.mInvisibility);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mKill.setOnClickListener(mKillOnClickListener);
        mRadar.setOnClickListener(mPowerUpOnClickListener);
        mInvisibility.setOnClickListener(mPowerUpOnClickListener);
        Utilities.init(this);
        Utilities.getImageLoader().displayImage("drawable://" + R.drawable.ezio, mTargetImage,
                new DisplayImageOptions.Builder().displayer(new CircleBitmapDisplayer()).build());
        setupMap();
    }

    private void setupMap() {
        map.setMyLocationEnabled(true);
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, mLocationListener);
        Location nLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location gLoc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (gLoc != null && nLoc != null) {
            if (gLoc.getAccuracy() > nLoc.getAccuracy()) {
                LatLng mLatLng = new LatLng(gLoc.getLatitude(), gLoc.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
            } else {
                LatLng mLatLng = new LatLng(nLoc.getLatitude(), nLoc.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
            }
        } else if (gLoc == null && nLoc != null) {
            LatLng mLatLng = new LatLng(nLoc.getLatitude(), nLoc.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
        } else if (nLoc == null && gLoc != null) {
            LatLng mLatLng = new LatLng(gLoc.getLatitude(), gLoc.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            mKillBitmap = (Bitmap) data.getExtras().get("data");
            mTargetImage.setImageBitmap(mKillBitmap);
        }
    }
}