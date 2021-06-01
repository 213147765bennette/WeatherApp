package com.example.weatherapp.http

import com.example.weatherapp.response.CurrentTaskResponse
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.response.TopCurrentResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
interface NetworkService {

    //get weather with hardcoded lat and long values
    @GET(WeatherApis.CURRENT_ENDPOINT)
    fun getTopCurrentWeather() : Single<TopCurrentResponse>

    //get weather with users location lat and long values
    @GET(WeatherApis.CURRENT_ENDPOINT_V2)
    fun getTopUserCurrentWeather(@Query("lat") lat:String,@Query("lon") lon:String) : Single<TopCurrentResponse>


    //get weather forcast using hardcoded lat and lon
    @GET(WeatherApis.FORECAST_ENDPOINT_COUNT)
    fun getFiveDaysForecast() : Single<FiveForecastResponse>

    //get weather forecast of users location using current lat and long values
    @GET(WeatherApis.FORECAST_ENDPOINT_COUNT_V2)
    fun getFiveUserDaysForecast(@Query("lat") lat:String,@Query("lon") lon:String) : Single<FiveForecastResponse>


    @GET(WeatherApis.CURRENT_ENDPOINT)
    fun getCurrentWeather() : Single<CurrentTaskResponse>

}