package com.aoinc.group1_location_nearbyplaces.util

class Constants {


    //-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=
    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
        const val API_PATH = "/maps/api/place/nearbysearch/json"
        const val QUERY_LOCATION = "location"
        const val QUERY_RADIUS = "radius"
        const val QUERY_KEY = "key"
        //const val TEST_PATH = "/maps/api/place/nearbysearch/json?location=-33.852,151.211&radius=150&key="
    }

}