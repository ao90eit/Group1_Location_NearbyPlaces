package com.aoinc.group1_location_nearbyplaces.util

class Constants {


    //-33.8670522,151.1957362&radius=1500&key=
    //TODO figure out how to pass key, coordinate and radius parameters
    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
        const val API_PATH = "/maps/api/place/nearbysearch/"
        const val QUERY = "/json?"
        const val QUERY_COORDINATES = "location="
        const val QUERY_RADIUS = "&radius="
        const val QUERY_KEY = "&key="
    }

}