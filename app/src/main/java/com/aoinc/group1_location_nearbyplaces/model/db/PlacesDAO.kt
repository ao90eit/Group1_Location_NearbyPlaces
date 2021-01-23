package com.aoinc.group1_location_nearbyplaces.model.db

import androidx.room.*
import com.google.android.libraries.places.api.Places
import com.aoinc.group1_location_nearbyplaces.model.data.Result as Result

@Dao
interface PlacesDAO {
    @Insert
    fun addPlace(vararg newPlace: Result?) // var args -> '...' for any number of args

    @Delete
    fun deletePlace(vararg deletePlace: Result?)

    @Query("SELECT * FROM Place WHERE place_id = :place_id")
    fun getPlace(place_id: Int): Result?

    @get:Query("SELECT * FROM Place ORDER BY geometry")
    val allPlaces: List<Places?>?

    @Update
    fun updatePlace(places: Places?)
}