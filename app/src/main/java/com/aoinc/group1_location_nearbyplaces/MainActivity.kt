package com.aoinc.group1_location_nearbyplaces

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE: Int = 1337

    private lateinit var permissionDeniedOverlay: ConstraintLayout
    private lateinit var goToAppSettingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    override fun onStart() {
        super.onStart()
        verifyLocationPermission()
    }

    private fun verifyLocationPermission() {
        if (hasLocationPermission()) {
            permissionDeniedOverlay.visibility = View.INVISIBLE
            registerLocationManager()
        } else
            requestLocationPermission()
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //only checking for location permission, can simplify outer conditionals a little
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                    requestLocationPermission()
                else
                    permissionDeniedOverlay.visibility = View.VISIBLE
            }
        }
    }

    private fun registerLocationManager() {
        TODO("Not yet implemented")
    }

    private fun hasLocationPermission(): Boolean =
            ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}