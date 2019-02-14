package com.example.nitin.jgi.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nitin.jgi.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override public void run() {
                Intent startactivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(startactivity);
                finish();
            }

        }, 2000);
    }
}
