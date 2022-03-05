package com.printf.shuttle_tracker.Model.Map.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.printf.shuttle_tracker.Model.Map.data.FirebaseDatabaseRetreiever.DataStatus
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class FirebaseDatabaseRetreiever(val pathName: String) {
    var database: FirebaseDatabase
    var locationContainer: DatabaseReference
    private var location: Location? = null

    interface DataStatus {
        fun DataIsLoaded(location: Location?)
    }

    init {
        database = FirebaseDatabase.getInstance()
        locationContainer = database.getReference(pathName)
    }

    fun getLocation(dataStatus: DataStatus) {
        locationContainer.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                location = snapshot.getValue(Location::class.java)
                Log.d("Firebase Database", "Database Collected")
                Log.d("Firebase Database", "Latitude:: " + location!!.latitude)
                Log.d("Firebase Database", "Longitude:: " + location!!.longitude)
                Log.d("Firebase Database", "isActive:: " + location!!.isActive)
                Log.d("Firebase Database", "Database Collected")
                dataStatus.DataIsLoaded(location)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}