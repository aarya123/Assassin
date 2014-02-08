package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.AssassinAndroid.AsyncTasks.PowerUpAsyncTask;
import com.AssassinAndroid.Tools.CircleBitmapDisplayer;
import com.AssassinAndroid.Tools.Utilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 12:12 AM
 */
public class TargetActivity extends Activity {
    ImageView mTargetImage, mRadar, mInvisibility;
    TextView mName, mSex, mRace, mHeight, mAge, mLocation, mFreqLocs;
    Button mKill;
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
        mFreqLocs = (TextView) findViewById(R.id.mFreqLocs);
        mKill = (Button) findViewById(R.id.mKill);
        mRadar = (ImageView) findViewById(R.id.mRadar);
        mInvisibility = (ImageView) findViewById(R.id.mInvisibility);
        mKill.setOnClickListener(mKillOnClickListener);
        mRadar.setOnClickListener(mPowerUpOnClickListener);
        mInvisibility.setOnClickListener(mPowerUpOnClickListener);
        Utilities.init(this);
        Utilities.getImageLoader().displayImage("drawable://" + R.drawable.ezio, mTargetImage,
                new DisplayImageOptions.Builder().displayer(new CircleBitmapDisplayer()).build());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mTargetImage.setImageBitmap(photo);
        }
    }
}