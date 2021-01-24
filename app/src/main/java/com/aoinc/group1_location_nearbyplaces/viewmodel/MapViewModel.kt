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
                .subscribe({
                    if (it.status == "OK") {
                        placesLiveData.postValue(it.results)
                        // TODO: send 'it.results' to db for offline use

                        Log.d("TAG_X", "${it}")
                    } else {
                        //TODO: handle other statuses
                        Log.d("TAG_X", "${it.status}")
                        Log.d("TAG_X", "${it.error_message}")
                    }
                    compositeDisposable.clear()
                }, {
                    Log.d("TAG_X", "${it.localizedMessage}")
                })
        )
    }

}