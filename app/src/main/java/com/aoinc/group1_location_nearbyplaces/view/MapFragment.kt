package com.aoinc.group1_location_nearbyplaces.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.model.data.Result
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapVMFactory
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    // get same instance of view model
    private val viewModel: MapViewModel by viewModels(
        factoryProducer = { MapVMFactory }
    )

    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.map_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.support_map_fragment) as SupportMapFragment
//        val mapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.support_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = view.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        viewModel.placesLiveData.observe(viewLifecycleOwner, {
            placeNearbyMarkers(it)
        })
    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()

        // stop polling for location to allow garbage collection
        locationManager.removeUpdates(this)
    }

    private fun placeNearbyMarkers(placesList: List<Result>) {
        for (place in placesList) {
            val pLocation = LatLng(place.geometry.location.lat, place.geometry.location.lng)
            mMap.addMarker(MarkerOptions()
                .position(pLocation)
                .title(place.name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }

    override fun onLocationChanged(location: Location) {
//        Log.d("LOCATION_CHECK", "My location is: ${location.latitude}, ${location.longitude}")
        viewModel.updateNearbyLocations(location)

        // check if lateinit var is initialized before setting location
        if (this::mMap.isInitialized) {
            Log.d("MAP_X", "map initialized")
            val curLocation = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(curLocation).title("My Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 14f))
        }
    }

    @SuppressLint("MissingPermission")
    // this should only load if permission was given anyway
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 20f, this)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }
}