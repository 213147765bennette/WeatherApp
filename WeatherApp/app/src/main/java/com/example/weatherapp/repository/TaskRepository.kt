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

    //here using hardcoded lat and lon values
    fun getTopCurrentWeather():Single<TopCurrentResponse> = networkService.getTopCurrentWeather()

    //using user locations
    fun getUserTopCurrentWeather(lat:String,lon:String):Single<TopCurrentResponse> = networkService.getTopUserCurrentWeather(lat,lon)

    //here using hardcoded lat lon values
    fun getFiveDaysForecast():Single<FiveForecastResponse> = networkService.getFiveDaysForecast()


    //here using the users current location lat lon
    fun getFiveDaysUserLocationForecast(lat:String,lon:String):Single<FiveForecastResponse> = networkService.getFiveUserDaysForecast(lat,lon)



    fun getCurrentWeather():Single<CurrentTaskResponse> = networkService.getCurrentWeather()


}