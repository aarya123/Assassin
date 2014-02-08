package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.AssassinAndroid.AsyncTasks.LogInAsyncTask;

/**
 * User: AnubhawArya
 * Date: 2/7/14
 * Time: 10:47 PM
 */
public class LogInActivity extends Activity {
    EditText mEmail, mPassword;
    Button mLogIn;
    View.OnClickListener mLogInClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            new LogInAsyncTask(LogInActivity.this).execute();
        }
    };
    View.OnFocusChangeListener mFocusChangeListener= new View.OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus)
                ((EditText)v).setHint("");
            else
                ((EditText)v).setHint(v==mEmail?"Email":"Password");
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mLogIn = (Button) findViewById(R.id.mLogIn);
        mLogIn.setOnClickListener(mLogInClickListener);
        mEmail.setOnFocusChangeListener(mFocusChangeListener);
        mPassword.setOnFocusChangeListener(mFocusChangeListener);
    }
}