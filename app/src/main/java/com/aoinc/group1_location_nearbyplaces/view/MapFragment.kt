package com.aoinc.group1_location_nearbyplaces.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapVMFactory
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapViewModel

class MapFragment : Fragment(), LocationListener {

    // get same instance of view model
    private val viewModel: MapViewModel by viewModels(
        factoryProducer = { MapVMFactory }
    )

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

        locationManager = view.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 20f, this)
    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()

        // stop polling for location to allow garbage collection
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
//        Log.d("LOCATION_CHECK", "My location is: ${location.latitude}, ${location.longitude}")
        viewModel.updateNearbyLocations(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }
}