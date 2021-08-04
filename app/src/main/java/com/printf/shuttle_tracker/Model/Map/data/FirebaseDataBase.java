package com.printf.shuttle_tracker.Model.Map.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class FirebaseDataBase {
    final private FirebaseDatabase mDatabase;
    final private DatabaseReference mreferenceLocations;
//    private List<Location> locations = new ArrayList<>();
    Location location;


    public FirebaseDataBase(String placeName){
        mDatabase = FirebaseDatabase.getInstance();
        mreferenceLocations = mDatabase.getReference(placeName);

    }

    public interface DataStatus{
        void DataIsLoaded(Location location, List<String> keys);
        void DataIsUpdated();
    }


    public void getLocation(final DataStatus dataStatus){
        mreferenceLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode: snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    location = keyNode.getValue(Location.class);
//                    locations.add(location);
                }
                dataStatus.DataIsLoaded(location, keys);

//                dataStatus.DataIsUpdated(locations, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
