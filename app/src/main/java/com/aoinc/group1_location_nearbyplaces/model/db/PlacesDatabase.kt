package com.aoinc.group1_location_nearbyplaces.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearchResponse
import com.aoinc.group1_location_nearbyplaces.util.Converters


@Database(version = 1, entities = [NearbySearchResponse.Result::class], exportSchema = true)
@TypeConverters(Converters::class)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun getPlacesDAO(): PlacesDAO

    companion object {
        const val DATABASE_NAME = "places.db"
    }
}