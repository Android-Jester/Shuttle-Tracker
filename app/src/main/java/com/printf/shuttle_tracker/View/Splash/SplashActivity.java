package com.printf.shuttle_tracker.View.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.printf.shuttle_tracker.R;
import com.printf.shuttle_tracker.View.Map.MapsActivity;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity {
    public Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            timer.wait(1000);
            startActivity(new Intent(this, MapsActivity.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            startActivity(new Intent(this, MapsActivity.class));
        }
    }

}