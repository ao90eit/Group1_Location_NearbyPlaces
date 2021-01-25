package com.aoinc.group1_location_nearbyplaces.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.group1_location_nearbyplaces.R
import com.aoinc.group1_location_nearbyplaces.model.data.Result
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapVMFactory
import com.aoinc.group1_location_nearbyplaces.viewmodel.MapViewModel

class NearbyLocationsFragment : Fragment(),
    LocationsRVAdapter.LocationListDelegate {

    // get same instance of view model
    private val viewModel: MapViewModel by viewModels(
        factoryProducer = { MapVMFactory }
    )

    // recycler view parts
    private lateinit var locationsRecyclerView: RecyclerView
    private val locationsRecyclerAdapter = LocationsRVAdapter(listOf(), this)
    private lateinit var thisLocationList: List<Result>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nearby_location_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationsRecyclerView = view.findViewById(R.id.location_recyclerView)
        locationsRecyclerView.adapter = locationsRecyclerAdapter

        // update location recycler view when new places are received
        viewModel.placesLiveData.observe(viewLifecycleOwner, {
            thisLocationList = it
            locationsRecyclerAdapter.updateLocationList(it)
        })
    }

    override fun onListItemSelected(position: Int) {
        // TODO: move to selected location on map
        Log.d("RV_CLICK", thisLocationList[position].placeId)
//        view.setBackgroundColor(Color.GREEN)
    }
}