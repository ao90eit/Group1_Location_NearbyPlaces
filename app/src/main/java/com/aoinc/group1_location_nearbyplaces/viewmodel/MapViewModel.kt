package com.aoinc.group1_location_nearbyplaces.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearchResponse
import com.aoinc.group1_location_nearbyplaces.network.PlacesRetrofit
import com.aoinc.group1_location_nearbyplaces.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val placesLiveData: MutableLiveData<List<NearbySearchResponse.Result>> = MutableLiveData()
    private val placesRetrofit: PlacesRetrofit = PlacesRetrofit()

    // manifest metadata
    lateinit var mapsKey : String

    fun getSearchResult(queryMap: Map<String,String>) {
        compositeDisposable.add(
            placesRetrofit.getNearbyPlaces(queryMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.placesStatus == "OK") {
                        placesLiveData.postValue(it.placesResults)
                        // TODO: send 'it.results' to db for offline use

                        for (r in it.placesResults)
                            Log.d("TAG_X", r.toString())
                    } else {
                        //TODO: handle other statuses
                        Log.d("TAG_X", it.placesStatus)
                        Log.d("TAG_X", it.placesError)
                    }
                    compositeDisposable.clear()
                }, {
                    Log.d("TAG_X", "${it.localizedMessage}")
                })
        )
    }

    fun updateNearbyLocations(location: Location) {
        val queryMap: Map<String,String> = mapOf(
            Constants.QUERY_LOCATION to "${location.latitude},${location.longitude}",
            Constants.QUERY_RADIUS to "1500",
            Constants.QUERY_KEY to mapsKey
            // TODO: protect API Key (but in a project shareable way)
        )

        getSearchResult(queryMap)
    }
}