package com.AssassinAndroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.AssassinAndroid.AsyncTasks.LogInAsyncTask;
import com.AssassinAndroid.Tools.Utilities;

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
            new LogInAsyncTask(LogInActivity.this).execute(mEmail.getText().toString(), mPassword.getText().toString());
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
        SharedPreferences settings = Utilities.getSharedPreferences(this);
        if(settings.contains("user_id")) {
        	Log.i("LoginActivity", "already logged in!");
        	Utilities.userId = settings.getString("user_id", "invalid");
        	Utilities.startTargetActivity(this);
        	finish();
        }
        setContentView(R.layout.login);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mLogIn = (Button) findViewById(R.id.mLogIn);
        mLogIn.setOnClickListener(mLogInClickListener);
        mEmail.setOnFocusChangeListener(mFocusChangeListener);
        mPassword.setOnFocusChangeListener(mFocusChangeListener);
        findViewById(R.id.mRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                LogInActivity.this.startActivity(intent);
            }
        });
    }
}