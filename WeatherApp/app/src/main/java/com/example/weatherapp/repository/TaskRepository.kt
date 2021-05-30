package com.example.weatherapp.repository

import com.example.weatherapp.http.NetworkService
import com.example.weatherapp.response.CurrentTaskResponse
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.response.TopCurrentResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
class TaskRepository(private val networkService: NetworkService) {

    fun getTopCurrentWeather():Single<TopCurrentResponse> = networkService.getTopCurrentWeather()

    fun getFiveDaysForecast():Single<FiveForecastResponse> = networkService.getFiveDaysForecast()

    fun getCurrentWeather():Single<CurrentTaskResponse> = networkService.getCurrentWeather()


}