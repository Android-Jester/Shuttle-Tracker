package com.printf.shuttleTracker.locationServices

import android.app.Application
import androidx.annotation.RequiresApi
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager

class App : Application() {
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Location Services",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    companion object {
        const val CHANNEL_ID = "LocationServices"
    }
}