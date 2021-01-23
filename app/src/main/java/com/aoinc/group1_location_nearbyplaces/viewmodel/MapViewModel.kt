package com.aoinc.group1_location_nearbyplaces.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aoinc.group1_location_nearbyplaces.model.data.Result
import com.aoinc.group1_location_nearbyplaces.network.PlacesRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.http.QueryMap

class MapViewModel: ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val placesLiveData: MutableLiveData<List<Result>> = MutableLiveData()

    private val placesRetrofit: PlacesRetrofit = PlacesRetrofit()
    fun getSearchResult(queryMap: Map<String,String>) {
        compositeDisposable.add(
            placesRetrofit.getNearbyPlaces(queryMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    it.results
                }
                .subscribe({
                    if (it.isNotEmpty())
                        placesLiveData.postValue(it)
                    Log.d("TAG_X", "${it}")
                    compositeDisposable.clear()
                }, {
                    Log.d("TAG_X", "${it.localizedMessage}")
                })
        )
    }

}