package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 11:06 AM
 */
public class RegisterActivity extends Activity {
    EditText mEmail, mPassword, mConfirmPassword, mAge, mRace, mFeet, mInches;
    TextView mErrorText;
    RadioButton mMale, mFemale;

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
                mErrorText.setText("");
            }
        });
    }
}