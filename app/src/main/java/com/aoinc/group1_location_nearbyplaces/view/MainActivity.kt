package com.aoinc.group1_location_nearbyplaces.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.model.db.PlacesDatabase
import com.aoinc.group1_location_nearbyplaces.util.Constants
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapVMFactory
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapViewModel

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE: Int = 1337

    // connected view objects
    private lateinit var permissionDeniedOverlay: ConstraintLayout
    private lateinit var goToAppSettingsButton: Button

    // get same instance of view model
    private val viewModel: MapViewModel by viewModels(
        factoryProducer = { MapVMFactory }
    )

    // manifest metadata
    private lateinit var mapsKey: String

    // dynamic fragments
    private val mapFragment = MapFragment()
    private val nearbyLocationsFragment = NearbyLocationsFragment()

    //Room
    private lateinit var placesDatabase: PlacesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get and set manifest metadata
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            .apply {
                mapsKey = metaData.getString("com.google.android.geo.API_KEY").toString()
            }
        viewModel.mapsKey = mapsKey

        // initialize view object connections
        permissionDeniedOverlay = findViewById(R.id.location_denied_overlay)
        goToAppSettingsButton = findViewById(R.id.go_to_app_settings_button)

        // open App Settings page
        goToAppSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        //initialize database
        placesDatabase = Room.databaseBuilder(
            this,
            PlacesDatabase::class.java,
            PlacesDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()

        viewModel.placesLiveData.observe(this, {
            for (place in it) {
                placesDatabase.getPlacesDAO().addPlace(place)
                Log.d("TAG_X", place.toString())
            }
        })
    }

    override fun onStart() {
        super.onStart()
        verifyLocationPermission()
    }

    private fun loadMainFragments() {
        // map fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.map_fragment_container_view, mapFragment)
            .commit()

        // location list fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.location_list_fragment_container_view, nearbyLocationsFragment)
            .commit()
    }

    private fun verifyLocationPermission() {
        if (hasLocationPermission()) {
            onLocationPermissionGranted()
        } else
            requestLocationPermission()
    }

    private fun onLocationPermissionGranted() {
        permissionDeniedOverlay.visibility = View.INVISIBLE
        loadMainFragments()   // only when permission granted
    }

    private fun hasLocationPermission(): Boolean =
        ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //only checking for location permission, can simplify outer conditionals a little
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLocationPermissionGranted()
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                    requestLocationPermission()
                else
                    permissionDeniedOverlay.visibility = View.VISIBLE
            }
        }
    }
}