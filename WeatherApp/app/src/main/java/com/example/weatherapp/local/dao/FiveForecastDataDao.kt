package com.example.weatherapp.local.dao

import androidx.room.*
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.response.FiveForecastResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
@Dao
interface FiveForecastDataDao {

    @Insert
    fun insert(fiveforecastEntity: FiveforecastEntity) : Single<Long>

    @Query("SELECT * from forecast ORDER BY id DESC")
    fun getAllFavourates(): Observable<List<FiveforecastEntity>>

}