package com.example.weatherapp.repository

import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.local.db.DatabaseService

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
class RoomRepository(private val databaseService: DatabaseService) {

    fun insert(fiveforecastEntity: FiveforecastEntity) = databaseService.fiveForecastDataDao().insert(fiveforecastEntity)
}