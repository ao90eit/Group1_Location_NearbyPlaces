package com.aoinc.group1_location_nearbyplaces.view.fragment

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
import androidx.fragment.app.Fragment
import com.aoinc.group1_location_nearbyplaces.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(callback)

        locationManager = view.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 5f, this)

    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onLocationChanged(location: Location) {
        Log.d("TAG_X", "${location.latitude} ${location.longitude}")

        if (this::mMap.isInitialized) {
            val currentLocation = LatLng(location.latitude, location.longitude)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(currentLocation))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 1f))
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //Needed for older Android device compatibility
    }
}