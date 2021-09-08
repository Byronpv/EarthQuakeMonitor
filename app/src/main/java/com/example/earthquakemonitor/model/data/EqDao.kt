package com.example.earthquakemonitor.model.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)
    @Query("SELECT * FROM earthquake")
    //Antes retornaba un MutableList de Earthquake
    fun getEarthquakes():  MutableList<Earthquake>

    @Query("SELECT * FROM earthquake ORDER BY magnitude DESC")
    fun getEarthquakesMagnitude(): MutableList<Earthquake>

}
