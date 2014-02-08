package com.AssassinAndroid.Activities;

import com.AssassinAndroid.AsyncTasks.RegisterAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 11:06 AM
 */
public class RegisterActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    EditText mEmail, mPassword, mConfirmPassword, mAge, mRace, mFeet, mInches;
    TextView mErrorText;
    RadioButton mMale, mFemale;
    ImageView mImageOne, mImageTwo, mImageThree;
    Bitmap one, two, three;
    LinearLayout mImageLayout;
    View.OnClickListener mImageOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
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
        mImageOne.setTag(false);
        mImageTwo.setTag(false);
        mImageThree.setTag(false);
        mImageLayout = (LinearLayout) findViewById(R.id.mImageLayout);
        mImageLayout.setOnClickListener(mImageOnClickListener);
        findViewById(R.id.mRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = "", password = "", race = "", gender = "";
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
                }else if (two == null) {
                    mErrorText.setText("Need photo #2");
                    return;
                }else if (three == null) {
                    mErrorText.setText("Need photo #3");
                    return;
                }
                mErrorText.setText("");
                new RegisterAsyncTask(this).execute(mEmail.getText().toString(), mPassword.getText().toString(), gender,
                		mRace.getText().toString(), mFeet.getText().toString());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (!(Boolean) mImageOne.getTag()) {
                mImageOne.setTag(true);
                one = (Bitmap) data.getExtras().get("data");
                mImageOne.setImageBitmap(one);
            } else if (!(Boolean) mImageTwo.getTag()) {
                mImageTwo.setTag(true);
                two = (Bitmap) data.getExtras().get("data");
                mImageTwo.setImageBitmap(two);
            } else if (!(Boolean) mImageThree.getTag()) {
                mImageThree.setTag(true);
                three = (Bitmap) data.getExtras().get("data");
                mImageThree.setImageBitmap(three);
                mImageLayout.setOnClickListener(null);
            }
        }
    }
}