package com.printf.shuttleTracker.locationServices

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import com.printf.shuttleTracker.locationServices.MainActivity
import com.printf.shuttleTracker.locationServices.LocationServiceReceiver
import android.widget.Toast
import android.app.ActivityManager
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var startOperation // used to switch the service on and off
            : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startOperation = findViewById(R.id.startOperation)
        startOperation!!.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(
                Intent(
                    Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                )
            )
        })
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_FINE_LOCATION
            )
        } else {
            startLocationService()
        }
    }

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_FINE_LOCATION
            )
        } else {
            startLocationService()
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_FINE_LOCATION
            )
        } else {
            startLocationService()
        }
    }

    fun startLocationService() {
        if (!isLocationServiceRunning) {
            startService(Intent(applicationContext, LocationServiceReceiver::class.java))
            Toast.makeText(this, "Location Services has started", Toast.LENGTH_SHORT).show()
        }
    }

    private val isLocationServiceRunning: Boolean
        private get() {
            val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            if (activityManager != null) {
                for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                    if (LocationServiceReceiver::class.java.name == service.service.className) {
                        if (service.foreground) {
                            return true
                        }
                    }
                }
                return false
            }
            return false
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // if permission is granted
        if (requestCode == PERMISSION_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // start location service
                startLocationService()
            } else {
                // didn't get permission, notify user
                Toast.makeText(
                    this,
                    "You must enable these permissions inorder to to use this app",
                    Toast.LENGTH_SHORT
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val PERMISSION_FINE_LOCATION =
            1 //permission request number, identifies the permission
    }
}