package com.aoinc.group1_location_nearbyplaces.model.data


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aoinc.group1_location_nearbyplaces.util.Converters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class NearbySearchResponse(
    @SerializedName("html_attributions")
    val placesHtmlAttributions: List<Any>?,
    @SerializedName("next_page_token")
    val placesNextPageToken: String?,
    @SerializedName("results")
    val placesResults: List<Result>,
    @SerializedName("status")
    val placesStatus: String,
    @SerializedName("error_message")
    val placesError: String
) {
    @Entity(tableName = "Place")
    data class Result(
        @SerializedName("business_status")
        val placesBusinessStatus: String?,
        @SerializedName("geometry")
        @Embedded
        val placesGeometry: Geometry,
        @SerializedName("icon")
        val placesIcon: String?,
        @SerializedName("name")
        val placesName: String,
        @SerializedName("opening_hours")
        @Embedded
        val placesOpeningHours: OpeningHours?,
        @SerializedName("photos")
        val placesPhotos: List<Photo>?,
        @PrimaryKey
        @SerializedName("place_id")
        val placesPlaceId: String,
        @SerializedName("plus_code")
        @Embedded
        val placesPlusCode: PlusCode?,
        @SerializedName("price_level")
        val placesPriceLevel: Int?,
        @SerializedName("rating")
        val placesRating: Double,
        @SerializedName("reference")
        val placesReference: String?,
        @SerializedName("scope")
        val placesScope: String?,
        @SerializedName("types")
        val placesTypes: List<String>?,
        @SerializedName("user_ratings_total")
        val placesUserRatingsTotal: Int?,
        @SerializedName("vicinity")
        val placesVicinity: String?
    ) {
        data class Geometry(
            @SerializedName("location")
            @Embedded
            val placesLocation: Location,
            @SerializedName("viewport")
            @Embedded
            val placesViewport: Viewport?
        ) {
            data class Location(
                @SerializedName("lat")
                val placesLat: Double,
                @SerializedName("lng")
                val placesLng: Double
            )

            data class Viewport(
                @SerializedName("northeast")
                @Embedded
                val placesNortheast: Northeast?,
                @SerializedName("southwest")
                @Embedded
                val placesSouthwest: Southwest?
            ) {
                data class Northeast(
                    @SerializedName("lat")
                    val placesNELat: Double?,
                    @SerializedName("lng")
                    val placesNELng: Double?
                )

                data class Southwest(
                    @SerializedName("lat")
                    val placesSWLat: Double?,
                    @SerializedName("lng")
                    val placesSWLng: Double?
                )
            }
        }

        data class OpeningHours(
            @SerializedName("open_now")
            val placesOpenNow: Boolean?
        )

        data class Photo(
            @SerializedName("height")
            val placesHeight: Int?,
            @SerializedName("html_attributions")
            val placesHtmlAttributions: List<String>?,
            @SerializedName("photo_reference")
            val placesPhotoReference: String?,
            @SerializedName("width")
            val placesWidth: Int?
        )

        data class PlusCode(
            @SerializedName("compound_code")
            val placesCompoundCode: String?,
            @SerializedName("global_code")
            val placesGlobalCode: String?
        )
    }
}