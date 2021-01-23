package com.aoinc.group1_location_nearbyplaces.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aoinc.group1_location_nearbyplaces.model.data.Result


@Database(version = 1, entities = [Result::class])
abstract class PlacesDatabase : RoomDatabase() {
    abstract val placesDAO: PlacesDAO?

    companion object {
        const val DATABASE_NAME = "places.db"
    }
}