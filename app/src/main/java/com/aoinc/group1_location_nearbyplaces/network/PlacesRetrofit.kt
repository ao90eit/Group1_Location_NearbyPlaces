package com.aoinc.group1_location_nearbyplaces.network

import android.util.Log
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearch
import com.aoinc.group1_location_nearbyplaces.util.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PlacesRetrofit {
    private var placesAPI: PlacesAPI

    init {
        placesAPI = createPlacesAPI(createRetrofitInstance())

    }

    private fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createPlacesAPI(retrofit: Retrofit): PlacesAPI =
        retrofit.create(PlacesAPI::class.java)

    fun getNearbyPlaces(queryMap: Map<String, String>): Observable<NearbySearch> {
        return placesAPI.getNearbyPlaces(queryMap)
    }
}