package com.aoinc.group1_location_nearbyplaces.model.db

import androidx.room.*
import com.aoinc.group1_location_nearbyplaces.model.data.NearbySearchResponse

@Dao
interface PlacesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlace(vararg newPlace: NearbySearchResponse.Result) // var args -> '...' for any number of args

    @Delete
    fun deletePlace(vararg deletePlace: NearbySearchResponse.Result)

    @Query("SELECT * FROM Place")
    fun allPlaces(): List<NearbySearchResponse.Result>

    @Update
    fun updatePlace(places: NearbySearchResponse.Result)
}