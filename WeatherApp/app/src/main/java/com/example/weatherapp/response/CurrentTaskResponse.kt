package com.example.weatherapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CurrentTaskResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Double,
    @SerializedName("current")
    val current: Current,
    @SerializedName("minutely")
    val minutely: List<Minutely>,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("alerts")
    val alerts: List<Alert>
) {
    @Keep
    data class Current(
        @SerializedName("dt")
        val dt: Double,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Double,
        @SerializedName("dew_poDouble")
        val dewPoDouble: Double,
        @SerializedName("uvi")
        val uvi: Double,
        @SerializedName("clouds")
        val clouds: Double,
        @SerializedName("visibility")
        val visibility: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        @SerializedName("wind_deg")
        val windDeg: Double,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("rain")
        val rain: Rain
    ) {
        @Keep
        data class Weather(
            @SerializedName("id")
            val id: Double,
            @SerializedName("main")
            val main: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val icon: String
        )

        @Keep
        data class Rain(
            @SerializedName("1h")
            val h: Double
        )
    }

    @Keep
    data class Minutely(
        @SerializedName("dt")
        val dt: Double,
        @SerializedName("precipitation")
        val precipitation: Double
    )

    @Keep
    data class Hourly(
        @SerializedName("dt")
        val dt: Double,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Double,
        @SerializedName("dew_poDouble")
        val dewPoDouble: Double,
        @SerializedName("uvi")
        val uvi: Double,
        @SerializedName("clouds")
        val clouds: Double,
        @SerializedName("visibility")
        val visibility: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        @SerializedName("wind_deg")
        val windDeg: Double,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("rain")
        val rain: Rain
    ) {
        @Keep
        data class Weather(
            @SerializedName("id")
            val id: Double,
            @SerializedName("main")
            val main: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val icon: String
        )

        @Keep
        data class Rain(
            @SerializedName("1h")
            val h: Double
        )
    }

    @Keep
    data class Daily(
        @SerializedName("dt")
        val dt: Double,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double,
        @SerializedName("moonrise")
        val moonrise: Double,
        @SerializedName("moonset")
        val moonset: Double,
        @SerializedName("moon_phase")
        val moonPhase: Double,
        @SerializedName("temp")
        val temp: Temp,
        @SerializedName("feels_like")
        val feelsLike: FeelsLike,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Double,
        @SerializedName("dew_poDouble")
        val dewPoDouble: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        @SerializedName("wind_deg")
        val windDeg: Double,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Double,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("rain")
        val rain: Double,
        @SerializedName("uvi")
        val uvi: Double
    ) {
        @Keep
        data class Temp(
            @SerializedName("day")
            val day: Double,
            @SerializedName("min")
            val min: Double,
            @SerializedName("max")
            val max: Double,
            @SerializedName("night")
            val night: Double,
            @SerializedName("eve")
            val eve: Double,
            @SerializedName("morn")
            val morn: Double
        )

        @Keep
        data class FeelsLike(
            @SerializedName("day")
            val day: Double,
            @SerializedName("night")
            val night: Double,
            @SerializedName("eve")
            val eve: Double,
            @SerializedName("morn")
            val morn: Double
        )

        @Keep
        data class Weather(
            @SerializedName("id")
            val id: Double,
            @SerializedName("main")
            val main: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val icon: String
        )
    }

    @Keep
    data class Alert(
        @SerializedName("sender_name")
        val senderName: String,
        @SerializedName("event")
        val event: String,
        @SerializedName("start")
        val start: Double,
        @SerializedName("end")
        val end: Double,
        @SerializedName("description")
        val description: String,
        @SerializedName("tags")
        val tags: List<String>
    )
}