package com.example.weatherapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
@Entity(tableName = "forecast")
data class FiveforecastEntity(

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "temperature")
    var temperature: String,
    @ColumnInfo(name = "temp_min")
    var temp_min: String,
    @ColumnInfo(name = "temp_max")
    var temp_max: String,
    @ColumnInfo(name = "main")
    var main: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "icon")
    var icon: String

)
