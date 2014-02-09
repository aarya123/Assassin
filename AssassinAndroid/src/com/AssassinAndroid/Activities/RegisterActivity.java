package com.AssassinAndroid.Activities;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import com.AssassinAndroid.AsyncTasks.RegisterAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.AssassinAndroid.Tools.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 11:06 AM
 */
public class RegisterActivity extends Activity {

    EditText mEmail, mPassword, mConfirmPassword, mAge, mRace, mFeet, mInches, mLocation,mName;
    TextView mErrorText;
    RadioButton mMale, mFemale;
    ImageView mImageOne, mImageTwo, mImageThree;
    File one, two, three;
    LinearLayout mImageLayout;
    Uri[] selectedImageUris = new Uri[3];
    int i = 0;
    View.OnClickListener mImageOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            openImageIntent();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Utilities.setupImageLoader(this);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mConfirmPassword = (EditText) findViewById(R.id.mConfirmPassword);
        mMale = (RadioButton) findViewById(R.id.mMale);
        mFemale = (RadioButton) findViewById(R.id.mFemale);
        mAge = (EditText) findViewById(R.id.mAge);
        mRace = (EditText) findViewById(R.id.mRace);
        mFeet = (EditText) findViewById(R.id.mFeet);
        mInches = (EditText) findViewById(R.id.mInches);
        mErrorText = (TextView) findViewById(R.id.mErrorText);
        mImageOne = (ImageView) findViewById(R.id.mImageOne);
        mImageTwo = (ImageView) findViewById(R.id.mImageTwo);
        mImageThree = (ImageView) findViewById(R.id.mImageThree);
        mLocation = (EditText) findViewById(R.id.mLocation);
        mName= (EditText) findViewById(R.id.mName);
        mImageOne.setTag(false);
        mImageTwo.setTag(false);
        mImageThree.setTag(false);
        mImageLayout = (LinearLayout) findViewById(R.id.mImageLayout);
        mImageLayout.setOnClickListener(mImageOnClickListener);
        findViewById(R.id.mRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = "", password = "", race = "", gender = "", location = "",name;
                int age, feet, inches;
                if (mEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                    email = mEmail.getText().toString();
                else {
                    mErrorText.setText("Invalid Email");
                    return;
                }
                if (!mPassword.getText().toString().equals("") &&
                        mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))
                    password = mPassword.getText().toString();
                else {
                    mErrorText.setText("Invalid Password");
                    return;
                }
                if (mMale.isChecked())
                    gender = "Male";
                else if (mFemale.isChecked())
                    gender = "Female";
                else {
                    mErrorText.setText("Invalid Gender");
                    return;
                }
                if (!mAge.getText().toString().equals(""))
                    age = Integer.parseInt(mAge.getText().toString());
                else {
                    mErrorText.setText("Invalid Age");
                    return;
                }
                if (!mRace.getText().toString().equals(""))
                    race = mRace.getText().toString();
                else {
                    mErrorText.setText("Invalid Race");
                    return;
                }
                if (!mLocation.getText().toString().equals(""))
                    location = mLocation.getText().toString();
                else {
                    mErrorText.setText("Invalid Location");
                    return;
                }
                if (!mFeet.getText().toString().equals(""))
                    feet = Integer.parseInt(mFeet.getText().toString());
                else {
                    mErrorText.setText("Invalid Feet");
                    return;
                }
                if (!mInches.getText().toString().equals(""))
                    inches = Integer.parseInt(mInches.getText().toString());
                else {
                    mErrorText.setText("Invalid Inches");
                    return;
                }
                if (one == null) {
                    mErrorText.setText("Need photo #1");
                    return;
                } else if (two == null) {
                    mErrorText.setText("Need photo #2");
                    return;
                } else if (three == null) {
                    mErrorText.setText("Need photo #3");
                    return;
                }
                if (!mName.getText().toString().equals(""))
                    name = mName.getText().toString();
                else {
                    mErrorText.setText("Invalid Name");
                    return;
                }
                mErrorText.setText("");
                new RegisterAsyncTask(RegisterActivity.this).execute(email, password, gender, age + "", race, (feet * 12 + inches) + "", one, two, three, location,name);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1234) {
            final boolean isCamera;
            isCamera = data == null || MediaStore.ACTION_IMAGE_CAPTURE.equals(data.getAction()) || "inline-data".equals(data.getAction());
            if (isCamera)
                if (i == 0) {
                    Utilities.getImageLoader().displayImage(selectedImageUris[i].toString(), mImageOne);
                } else if (i == 1) {
                    Utilities.getImageLoader().displayImage(selectedImageUris[i].toString(), mImageTwo);
                } else {
                    Utilities.getImageLoader().displayImage(selectedImageUris[i].toString(), mImageThree);
                    mImageLayout.setOnClickListener(null);
                }
            i++;
        }
    }

    private void openImageIntent() {
        // Camera.
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File parentDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "Assassin" + File.separator);
        parentDirectory.mkdirs();
        if (i == 0) {
            one = new File(parentDirectory, "IMG_" + (int) (Math.random() * 3451) + ".jpg");
            selectedImageUris[i] = Uri.fromFile(one);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUris[i]);
        } else if (i == 1) {
            two = new File(parentDirectory, "IMG_" + (int) (Math.random() * 3451) + ".jpg");
            selectedImageUris[i] = Uri.fromFile(two);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUris[i]);
        } else {
            three = new File(parentDirectory, "IMG_" + (int) (Math.random() * 3451) + ".jpg");
            selectedImageUris[i] = Uri.fromFile(three);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUris[i]);
        }
        startActivityForResult(captureIntent, 1234);
    }
}