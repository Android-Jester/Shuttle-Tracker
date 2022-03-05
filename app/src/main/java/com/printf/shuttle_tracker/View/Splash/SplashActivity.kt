package com.printf.shuttle_tracker.View.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.printf.shuttle_tracker.R
import com.printf.shuttle_tracker.View.Map.MapsActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        try {
            startActivity(Intent(this, MapsActivity::class.java))
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}