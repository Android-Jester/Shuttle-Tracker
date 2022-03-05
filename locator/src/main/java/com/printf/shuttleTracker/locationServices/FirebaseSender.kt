package com.printf.shuttleTracker.locationServices

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseError

class FirebaseSender internal constructor(latitude: Double, longitude: Double, isActive: Boolean) {
    private val database = FirebaseDatabase.getInstance()
    var latitudeContainer = database.getReference("commericalLocationA").child("latitude")
    var longitudeContainer = database.getReference("commericalLocationA").child("longitude")
    var activeContainer = database.getReference("commericalLocationA").child("isActive")

    init {
        latitudeContainer.setValue(latitude)
        longitudeContainer.setValue(longitude)
        activeContainer.setValue(isActive) { error: DatabaseError?, ref: DatabaseReference? ->
            if (error != null) {
                Log.d("Firebase Error", error.toString())
            }
        }
    }
}