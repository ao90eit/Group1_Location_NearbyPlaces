package com.aoinc.group1_location_nearbyplaces.util

class Constants {


    //-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=
    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
        const val API_PATH = "/maps/api/place/nearbysearch/"
        const val QUERY_NEARBY_LOCATIONS = "/json?location="
        const val QUERY_RADIUS = "&radius="
        const val QUERY_KEY = "&key="
    }

}