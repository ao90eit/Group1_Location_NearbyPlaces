package com.aoinc.group1_location_nearbyplaces.util

import androidx.room.TypeConverter
import com.aoinc.group1_location_nearbyplaces.model.data.*
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearchResponse.Result.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    /*    @TypeConverter
        fun listToGeometry(value: List<Geometry>?): String? {
            value?.let {
                return Gson().toJson(value)
            } ?: return null
        }

        @TypeConverter
        fun geometryToList(value: String?): List<Geometry>? {
            val objects = Gson().fromJson(value, Array<Geometry>::class.java)
            return objects?.toList()
        }

        @TypeConverter
        fun openingHoursToBoolean(openingHours: OpeningHours?): Boolean {
            return openingHours?.openNow ?: false
        }

        @TypeConverter
        fun booleanToOpeningHours(bool: Boolean): OpeningHours {
            return OpeningHours(bool)
        }

        @TypeConverter
        fun listToPhoto(value: List<Photo>?): String? {
            value?.let {
                return Gson().toJson(value)
            } ?: return null
        }

        @TypeConverter
        fun photoToList(value: String?): List<Photo>? {
            val objects = Gson().fromJson(value, Array<Photo>::class.java)
            return objects?.toList()
        }

        @TypeConverter
        fun listToPlusCode(value: List<PlusCode>?): String? {
            value?.let {
                return Gson().toJson(value)
            } ?: return null
        }

        @TypeConverter
        fun plusCodeToList(value: String?): List<PlusCode>? {
            val objects = Gson().fromJson(value, Array<PlusCode>::class.java)
            return objects?.toList()
        }*/
    private val gson: Gson = Gson()

    @TypeConverter
    fun listToString(typesList: List<String>): String {
        return gson.toJson(typesList)
    }

    @TypeConverter
    fun stringToList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun photosToString(photoList: List<Photo>): String {
        return gson.toJson(photoList)
    }

    @TypeConverter
    fun stringToPhotos(data: String): List<Photo> {
        val listType = object : TypeToken<List<Photo>>() {}.type
        return gson.fromJson(data, listType)
    }

}