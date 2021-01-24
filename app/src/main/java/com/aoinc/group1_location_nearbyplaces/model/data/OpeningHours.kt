package com.aoinc.group1_location_nearbyplaces.model.data


import com.google.gson.annotations.SerializedName

data class OpeningHours(
    @SerializedName("open_now")
    val openNow: Boolean
)