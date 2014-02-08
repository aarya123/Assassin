package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * User: AnubhawArya
 * Date: 2/8/14
 * Time: 11:06 AM
 */
public class RegisterActivity extends Activity {
    EditText mEmail, mPassword, mConfirmPassword, mAge, mRace, mFeet, mInches;
    Spinner mGender;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mConfirmPassword = (EditText) findViewById(R.id.mConfirmPassword);
        mGender = (Spinner) findViewById(R.id.mGender);
        mAge = (EditText) findViewById(R.id.mAge);
        mRace = (EditText) findViewById(R.id.mRace);
        mFeet = (EditText) findViewById(R.id.mFeet);
        mInches = (EditText) findViewById(R.id.mInches);
        findViewById(R.id.mRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email, password, race, gender;
                int age, feet, inches;
                email=mEmail.getText().toString();
                if(mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))
                    password=mPassword.getText().toString();
            }
        });
    }
}