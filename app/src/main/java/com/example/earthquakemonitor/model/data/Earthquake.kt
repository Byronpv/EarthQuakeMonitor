package com.example.earthquakemonitor.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "earthquake")
@Parcelize
data class Earthquake(
    @PrimaryKey val id: String,
    val place: String,
    val magnitude: Double,
    val time: Long,
    val latitude: Double,
    val longitude: Double
) : Parcelable {
}

