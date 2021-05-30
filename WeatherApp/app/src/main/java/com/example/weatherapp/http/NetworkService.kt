package com.example.weatherapp.http

import com.example.weatherapp.response.CurrentTaskResponse
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.response.TopCurrentResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
interface NetworkService {

    @GET(WeatherApis.CURRENT_ENDPOINT)
    fun getTopCurrentWeather() : Single<TopCurrentResponse>

    @GET(WeatherApis.FORECAST_ENDPOINT_COUNT)
    fun getFiveDaysForecast() : Single<FiveForecastResponse>

    @GET(WeatherApis.CURRENT_ENDPOINT)
    fun getCurrentWeather() : Single<CurrentTaskResponse>

}