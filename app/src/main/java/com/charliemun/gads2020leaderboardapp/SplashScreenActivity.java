package com.charliemun.gads2020leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loadMainActivity();
    }

    private void loadMainActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainActivityIntent = new Intent(
                                            SplashScreenActivity.this,
                                            MainActivity.class);
                                    startActivity(mainActivityIntent);
                                    finish();
                                }
                            },
                3000);
    }
}