package com.aoinc.group1_location_nearbyplaces.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearchResponse
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

    // location polling
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap

    // network connectivity
    private lateinit var connectivityManager: ConnectivityManager
    private val networkRequestBuilder: NetworkRequest.Builder = NetworkRequest.Builder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.map_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.support_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // get connectivity system service
        connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // get location manager service
        locationManager = view.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        viewModel.placesLiveData.observe(viewLifecycleOwner, {
            placeNearbyMarkers(it)
        })

        viewModel.isNetworkConnected.observe(viewLifecycleOwner, {
            if (it) startLocationPolling() else stopLocationPolling()
            Log.d("TAG_OFFLINE", it.toString())
            // TODO: enable/disable 'no connection' text
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // register network connectivity listener
    override fun onResume() {
        super.onResume()

        connectivityManager.registerNetworkCallback(
            networkRequestBuilder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    Log.d("TAG_NETWORK", "Network available.")
                    viewModel.updateNetworkState(true)
                }

                override fun onLost(network: Network) {
                    Log.d("TAG_NETWORK", "Network lost.")
                    viewModel.updateNetworkState(false)
                }
            })
    }

    @SuppressLint("MissingPermission")
    override fun onPause() {
        super.onPause()

        // unregister network connectivity listener
        connectivityManager.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())

        // stop polling for location to allow garbage collection
        stopLocationPolling()
    }

    @SuppressLint("MissingPermission")
    // this fragment should only load if permission was given anyway
    private fun startLocationPolling() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 20f, this)
    }

    private fun stopLocationPolling() {
        locationManager.removeUpdates(this)
    }

    private fun placeNearbyMarkers(placesList: List<NearbySearchResponse.Result>) {
        for (place in placesList) {
            val pLocation = LatLng(place.placesGeometry.placesLocation.placesLat,
                place.placesGeometry.placesLocation.placesLng)
            mMap.addMarker(MarkerOptions()
                .position(pLocation)
                .title(place.placesName)
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        startLocationPolling()
    }
}