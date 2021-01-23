package com.aoinc.group1_location_nearbyplaces.model.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Place")
data class Result(
    @SerializedName("business_status")
    val businessStatus: String,
    @ColumnInfo(name="geometry")
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("icon")
    val icon: String,
    @ColumnInfo(name="name")
    @SerializedName("name")
    val name: String,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours,
    @SerializedName("photos")
    val photos: List<Photo>,
    @PrimaryKey
    @ColumnInfo(name="place_id")
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("plus_code")
    val plusCode: PlusCode,
    @SerializedName("price_level")
    val priceLevel: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("types")
    val types: List<String>,
    @SerializedName("user_ratings_total")
    val userRatingsTotal: Int,
    @SerializedName("vicinity")
    val vicinity: String
)