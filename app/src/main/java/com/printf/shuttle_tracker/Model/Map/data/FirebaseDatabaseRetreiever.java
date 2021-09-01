package com.printf.shuttle_tracker.Model.Map.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseDatabaseRetreiever {
    final String pathName;
    public FirebaseDatabase database;
    public DatabaseReference locationContainer;
    private Location location;

    public interface DataStatus {
        void DataIsLoaded(Location location);
    }

    public FirebaseDatabaseRetreiever(String pathName) {
        this.pathName = pathName;
        database = FirebaseDatabase.getInstance();
        locationContainer = database.getReference(pathName);

    }


    public void getLocation(final DataStatus dataStatus) {
        locationContainer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                location = snapshot.getValue(Location.class);

                Log.d("Firebase Database", "Database Collected");
                Log.d("Firebase Database", "Latitude:: " + location.getLatitude());
                Log.d("Firebase Database", "Longitude:: " + location.getLongitude());
                Log.d("Firebase Database", "isActive:: " + location.isActive());

                Log.d("Firebase Database", "Database Collected");
                dataStatus.DataIsLoaded(location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}