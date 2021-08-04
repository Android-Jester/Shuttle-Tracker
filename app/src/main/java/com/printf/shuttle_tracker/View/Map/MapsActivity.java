package com.printf.shuttle_tracker.View.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.printf.shuttle_tracker.Model.Map.data.FirebaseDataBase;
import com.printf.shuttle_tracker.Model.Map.data.Location;
import com.printf.shuttle_tracker.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapsActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private FirebaseDataBase CommericallocationA = new FirebaseDataBase("CommericallocationA");
    private FirebaseDataBase CommericallocationB = new FirebaseDataBase("CommericallocationB");
    private FirebaseDataBase BrunailocationA = new FirebaseDataBase("BrunailocationA");
    private FirebaseDataBase BrunailocationB = new FirebaseDataBase("BrunailocationB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(18.0);
        map.setMultiTouchControls(true);


        CommericallocationA.getLocation(new FirebaseDataBase.DataStatus() {
            @Override
            public void DataIsLoaded(Location location, List<String> keys) {
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                Marker startMaker = new Marker(map);
                startMaker.setPosition(point);
                startMaker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                map.getOverlays().add(startMaker);
            }

            @Override
            public void DataIsUpdated() {

            }
        });
        CommericallocationB.getLocation(new FirebaseDataBase.DataStatus() {
            @Override
            public void DataIsLoaded(Location location, List<String> keys) {
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                Marker startMaker = new Marker(map);
                startMaker.setPosition(point);
                startMaker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                map.getOverlays().add(startMaker);
            }

            @Override
            public void DataIsUpdated() {

            }
        });
        BrunailocationA.getLocation(new FirebaseDataBase.DataStatus() {
            @Override
            public void DataIsLoaded(Location location, List<String> keys) {
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                Marker startMaker = new Marker(map);
                startMaker.setPosition(point);
                startMaker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                map.getOverlays().add(startMaker);
            }

            @Override
            public void DataIsUpdated() {

            }
        });
        BrunailocationB.getLocation(new FirebaseDataBase.DataStatus() {
            @Override
            public void DataIsLoaded(Location location, List<String> keys) {
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                Marker startMaker = new Marker(map);
                startMaker.setPosition(point);
                startMaker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                map.getOverlays().add(startMaker);
            }

            @Override
            public void DataIsUpdated() {

            }
        });

        //setting up the mapview


        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION
        });
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        map.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(this, map);
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);

    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        permissionsToRequest.addAll(Arrays.asList(permissions).subList(0, grantResults.length));
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(
                            new String[0]
                    ), REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }

    }

    private void requestPermissionsIfNecessary(String[] permissions){
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }




}



