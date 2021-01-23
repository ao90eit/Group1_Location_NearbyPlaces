package com.aoinc.group1_location_nearbyplaces.network

import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearch
import com.aoinc.group1_location_nearbyplaces.util.Constants
import com.aoinc.group1_location_nearbyplaces.util.Constants.Companion.API_PATH
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI {
    @GET(API_PATH)
    fun getNearbyPlaces(@Query(Constants.QUERY_NEARBY_LOCATIONS) searchQuery: String): Observable<NearbySearch>
}