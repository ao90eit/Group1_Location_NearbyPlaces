package com.aoinc.group1_location_nearbyplaces

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

class MapFragment : Fragment(), LocationListener {

    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.map_fragment_layout, container, false)

    @SuppressLint("MissingPermission")
    // this should only load if permission was given anyway
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 20f, this)
    }

    override fun onStop() {
        super.onStop()

        // stop polling for location to allow garbage collection
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        // TODO request new search result from viewmodel
        Log.d("LOCATION_CHECK", "My location is: ${location.latitude}, ${location.longitude}")
    }
}