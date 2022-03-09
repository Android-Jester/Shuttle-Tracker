package com.printf.shuttle_tracker.View.Map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.util.GeoPoint
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import com.printf.shuttle_tracker.Model.Map.data.FirebaseDatabaseRetreiever
import com.google.android.gms.location.LocationServices
import com.printf.shuttle_tracker.View.Login.LoginActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.printf.shuttle_tracker.R
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.util.*

class MapsActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private var map: MapView? = null

    fun makeMarker(point: GeoPoint?): Marker {
        val startMarker = Marker(map)
        startMarker.position = point
        startMarker.setDefaultIcon()
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        map!!.overlays.clear()
        map!!.overlays.add(startMarker)
        map!!.controller.setCenter(point)
        map!!.controller.setZoom(20.0)
        return startMarker
    }

    private var auth: FirebaseAuth? = null
    val geoPoints: MutableList<GeoPoint> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val context = applicationContext
        Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
        auth = FirebaseAuth.getInstance()

        //setting up the mapview
        map = findViewById(R.id.map)
        map!!.setTileSource(TileSourceFactory.MAPNIK)
        map!!.getController().setZoom(15.0)
        map!!.setMultiTouchControls(true)
        map!!.canZoomIn()
        map!!.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        requestPermissionsIfNecessary(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )


        //TODO:START LINES after fixing performance issues
        val line = Polyline() //see note below!
        line.setPoints(geoPoints)
        line.setOnClickListener { polyline, mapView, eventPos ->
            Toast.makeText(
                mapView.context,
                "polyline with " + polyline.points.size + "pts was tapped",
                Toast.LENGTH_LONG
            ).show()
            false
        }
        map!!.getOverlayManager().add(line)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth!!.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        val retriever = FirebaseDatabaseRetreiever("commericalLocationA")
        retriever.getLocation { location ->
            val startPoint = GeoPoint(
                location.latitude,
                location.longitude
            ) //Gets a geopoint of the location recieved
            makeMarker(startPoint)
            val intent = Intent(applicationContext, LocationServices::class.java)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val userGeo = GeoPoint(latitude, longitude)
            geoPoints.add(startPoint)
            geoPoints.add(userGeo)
        }
    }

    public override fun onResume() {
        super.onResume()
        map!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map!!.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        permissionsToRequest.addAll(Arrays.asList(*permissions).subList(0, grantResults.size))
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(), REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}