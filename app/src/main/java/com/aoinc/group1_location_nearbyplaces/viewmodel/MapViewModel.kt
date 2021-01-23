package com.aoinc.group1_location_nearbyplaces.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aoinc.group1_location_nearbyplaces.model.data.Result
import com.aoinc.group1_location_nearbyplaces.network.PlacesRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapViewModel: ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val placesLiveData: MutableLiveData<List<Result>> = MutableLiveData()

    private val placesRetrofit: PlacesRetrofit = PlacesRetrofit()
    fun getSearchResult(searchQuery: String) {
        compositeDisposable.add(
            placesRetrofit.getNearbyPlaces(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    it.results
                }
                .subscribe({
                    if (it.isNotEmpty())
                        placesLiveData.postValue(it)
                    compositeDisposable.clear()
                }, {
                    Log.d("TAG_X", "${it.localizedMessage}")
                })
        )
    }

}