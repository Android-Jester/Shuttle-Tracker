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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.j256.ormlite.stmt.query.In;
import com.printf.shuttle_tracker.Model.Map.data.FirebaseDatabaseRetreiever;
import com.printf.shuttle_tracker.Model.Map.data.Location;
import com.printf.shuttle_tracker.R;
import com.printf.shuttle_tracker.View.Login.LoginActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class MapsActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
//    private boolean isCom;

//    public Button isCommericalLocation;
//    public Button isBrunaiLocation;
//    public TextView distanceText;
//
//    private Button rightButton;
//    private Button leftButton;
    public int index = 0;


    Marker makeMarker(GeoPoint point){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setDefaultIcon();
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

        map.getOverlays().clear();
        map.getOverlays().add(startMarker);
        map.getController().setCenter(point);
        map.getController().setZoom(20.0);
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
        map.canZoomIn();
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

        List<GeoPoint> geoPoints = new ArrayList<>();

        requestPermissionsIfNecessary(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION});


        FirebaseDatabaseRetreiever retreiever = new FirebaseDatabaseRetreiever("commericalLocationA");

        retreiever.getLocation(new FirebaseDatabaseRetreiever.DataStatus() {
            @Override
            public void DataIsLoaded(Location location) {
                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
                            makeMarker(startPoint);

                Intent intent = new Intent(getApplicationContext(), LocationServices.class);
                        double longitude = intent.getDoubleExtra("longitude", 0.0);
                        double latitude = intent.getDoubleExtra("latitude", 0.0);
                GeoPoint userGeo = new GeoPoint(latitude, longitude);

                geoPoints.add(startPoint);
                geoPoints.add(userGeo);


//
            }
        });

        //TODO:START LINES after fixing performance issues
////add your points here
        Polyline line = new Polyline();   //see note below!
        line.setPoints(geoPoints);
        line.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                Toast.makeText(mapView.getContext(), "polyline with " + polyline.getPoints().size() + "pts was tapped", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        map.getOverlayManager().add(line);

//        List<FirebaseDatabaseRetreiever> commericalLocation = new ArrayList<>();
//        commericalLocation.add(new FirebaseDatabaseRetreiever("commericalLocationA"));
//        commericalLocation.add(new FirebaseDatabaseRetreiever("commericalLocationB"));
//
//        List<FirebaseDatabaseRetreiever> brunaiLocation = new ArrayList<>();
//        brunaiLocation.add(new FirebaseDatabaseRetreiever("brunaiLocationA"));
//        brunaiLocation.add(new FirebaseDatabaseRetreiever("brunaiLocationB"));
//
//
//        isCommericalLocation.setOnClickListener(v->{
//            isCom = true;
//        });
//
//        isBrunaiLocation.setOnClickListener(v->{
//            isCom = false;
//        });
//
//
//
//        //Initializing the Database to retrieve the location from the database
//        if(isCom) {
//
//            commericalLocation.get(0).getLocation(
//                    location -> {
//                        if (location.isActve()) {
//                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                            makeMarker(startPoint);
//                        }
//                    }
//            );
//
//            commericalLocation.get(1).getLocation(
//                    location -> {
//                        if (location.isActve()) {
//                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                            makeMarker(startPoint);
//                        }
//                    }
//            );
//        } else {
//
//            brunaiLocation.get(0).getLocation(
//                    location -> {
//                        if (location.isActve()) {
//                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                            makeMarker(startPoint);
//                        }
//                    }
//            );
//
//            brunaiLocation.get(1).getLocation(
//                    location -> {
//                        if (location.isActve()) {
//                            GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                            makeMarker(startPoint);
//                        }
//                    }
//            );
//        }
//
//
//
//
//        leftButton.setOnClickListener(v -> {
//
//            if(isCom && commericalLocation.size() < 3) {
//                commericalLocation.get(index).getLocation(new FirebaseDatabaseRetreiever.DataStatus() {
//                    @Override
//                    public void DataIsLoaded(Location location) {
//                        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                        map.getController().setCenter(startPoint);
//                    }
//
//
//
//                });
//                if(index < 2){
//                    index = 0;
//                }
//                index--;
//            } else if (brunaiLocation.size() < 3) {
//                brunaiLocation.get(index).getLocation(new FirebaseDatabaseRetreiever.DataStatus() {
//                    @Override
//                    public void DataIsLoaded(Location location) {
//                        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                        map.getController().setCenter(startPoint);
//                    }
//
//
//
//                });
//                if(index < 0){
//                    index = 0;
//                }
//                index--;
//            }
//        });
//        rightButton.setOnClickListener(v -> {
//
//            if(isCom && commericalLocation.size() < 3) {
//                commericalLocation.get(index).getLocation(new FirebaseDatabaseRetreiever.DataStatus() {
//                    @Override
//                    public void DataIsLoaded(Location location) {
//                        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                        map.getController().setCenter(startPoint);
//                    }
//
//
//
//                });
//                if(index > 2){
//                    index = 0;
//                }
//                index++;
//            } else if (brunaiLocation.size() < 3) {
//                brunaiLocation.get(index).getLocation(new FirebaseDatabaseRetreiever.DataStatus() {
//                    @Override
//                    public void DataIsLoaded(Location location) {
//                        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Gets a geopoint of the location recieved
//                        map.getController().setCenter(startPoint);
//                    }
//
//
//
//                });
//                if(index > 2){
//                    index = 0;
//                }
//                index++;
//            }
//        });



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



