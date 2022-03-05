package com.printf.shuttle_tracker.Model.Map.data

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.printf.shuttle_tracker.Model.Map.data.LocationService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.printf.shuttle_tracker.View.Map.MapsActivity
import com.google.android.gms.location.LocationServices

internal class LocationService : Service() {
    private val TAG = LocationService::class.java.simpleName
    private var locationCallBack //Used for receiving notifications from the FusedLocationProviderApi
            : LocationCallback? = null

    // when the device location has changed or can no longer be determined
    private var location // last known location or updated location
            : Location? = null
    private var locationRequest //
            : LocationRequest? = null
    private var latitude = 0.0
    private var longitude = 0.0
    override fun onCreate() {
        super.onCreate()
        Log.d("Service Detector", "Started Created")
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation
                location = locationResult.lastLocation
                latitude = location!!.latitude
                longitude = location!!.longitude
                Log.d(TAG, "$latitude, $longitude, ")
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startLocationService()
        Toast.makeText(applicationContext, "Location Started", Toast.LENGTH_SHORT).show()
        Log.d("Service Detector", "Started Service")
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun startLocationService() {
        locationRequest = LocationRequest()
        configLocationRequest()
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest!!, locationCallBack!!, Looper.getMainLooper())
    }

    fun configLocationRequest() {
        locationRequest!!.interval =
            DEFAULT_INTERVAL.toLong() // set interval in which we want to get location in
        locationRequest!!.fastestInterval = FASTEST_INTERVAL.toLong()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onDestroy() {
        Log.d(TAG, "*****DESTROY****")
        stopLocationService()
    }

    override fun stopService(name: Intent): Boolean {
        Log.d(TAG, "STOP SERVICE")
        stopLocationService()
        return super.stopService(name)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        Log.d(TAG, "ON TASK REMOVED")
        super.onTaskRemoved(rootIntent)
    }

    private fun stopLocationService() {
        Log.d("location", "*****STOPPED****")
        // Remove all location updates for the given location result listener
        LocationServices.getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallBack!!)
        // stop the service and remove the notification
//        stopForeground(true);
        stopSelf()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val DEFAULT_INTERVAL = 4000 // default location update interval
        const val FASTEST_INTERVAL = 2000 // fastest location update interval
    }
}