package com.printf.shuttle_tracker.View.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.printf.shuttle_tracker.Model.Map.data.FirebaseDatabaseRetreiever;
import com.printf.shuttle_tracker.Model.Map.data.Location;
import com.printf.shuttle_tracker.R;
import com.printf.shuttle_tracker.View.Login.LoginActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapsActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;


    Marker makeMarker(GeoPoint point){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setTextIcon(point.getLatitude() + " , " + point.getLongitude());
        Log.d("GEOPOINT", String.valueOf(point.getLatitude() + point.getLongitude()));

        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        map.getOverlays().clear();
        map.getOverlays().add(startMarker);
        map.getController().setCenter(point);


        return startMarker;
    }


    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        auth = FirebaseAuth.getInstance();
        //setting up the mapview
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(15.0);
        map.setMultiTouchControls(true);




        requestPermissionsIfNecessary(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION});
//        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        map.setMultiTouchControls(true);


        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);

        //Initializing the Database to retrieve the location from the database
        FirebaseDatabaseRetreiever com1 = new FirebaseDatabaseRetreiever("commericalLocationA");
        com1.getLocation(location -> {
            GeoPoint startPoint = new GeoPoint(location.getLatitude(),  location.getLongitude()); //Gets a geopoint of the location recieved
            makeMarker(startPoint);
        });


        //TODO: An Overlay that would select the bus locations before the markers are set


//        List<GeoPoint> CommericalAreaPoints = new ArrayList<>();
//        CommericalAreaPoints.add(new GeoPoint(com1.getLatitude(), com1.getLongitude()));
//        CommericalAreaPoints.add(new GeoPoint(com2.getLatitude(), com2.getLongitude()));
//
//
//        List<GeoPoint> BrunaiAreaPoints = new ArrayList<>();
//        CommericalAreaPoints.add(new GeoPoint(bru1.getLatitude(), bru1.getLongitude()));
//        CommericalAreaPoints.add(new GeoPoint(bru2.getLatitude(), bru2.getLongitude()));
//
//
//        if(isCom && isActive) {
//            List<Marker> comMarkers = new ArrayList<>();
//            comMarkers.add(makeMarker(CommericalAreaPoints.get(0)));
//            comMarkers.add(makeMarker(CommericalAreaPoints.get(1)));
//        } else {
//            List<Marker> bruMarkers = new ArrayList<>();
//            bruMarkers.add(makeMarker(BrunaiAreaPoints.get(0)));
//            bruMarkers.add(makeMarker(BrunaiAreaPoints.get(1)));
//        }

        //TODO: Set The Two buttons to center at the geological point upon click




    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



