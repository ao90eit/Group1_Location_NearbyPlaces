package com.aoinc.group1_location_nearbyplaces.model.data


import com.google.gson.annotations.SerializedName

data class NearbySearch(
    @SerializedName("html_attributions")
    val htmlAttributions: List<Any>,
    @SerializedName("next_page_token")
    val nextPageToken: String,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String,
    @SerializedName("error_message")
    val error_message: String
)