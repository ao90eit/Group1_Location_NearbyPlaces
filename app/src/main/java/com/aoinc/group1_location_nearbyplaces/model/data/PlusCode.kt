package com.aoinc.group1_location_nearbyplaces.model.data


import com.google.gson.annotations.SerializedName

data class PlusCode(
    @SerializedName("compound_code")
    val compoundCode: String,
    @SerializedName("global_code")
    val globalCode: String
)