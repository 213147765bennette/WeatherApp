package com.example.weatherapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TopCurrentResponse(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Double,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Double,
    @SerializedName("id")
    val id: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Double
) {
    @Keep
    data class Coord(
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("lat")
        val lat: Double
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

    @Keep
    data class Main(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("temp_min")
        val tempMin: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Double,
        @SerializedName("sea_level")
        val seaLevel: Double,
        @SerializedName("grnd_level")
        val grndLevel: Double
    )

    @Keep
    data class Wind(
        @SerializedName("speed")
        val speed: Double,
        @SerializedName("deg")
        val deg: Double,
        @SerializedName("gust")
        val gust: Double
    )

    @Keep
    data class Clouds(
        @SerializedName("all")
        val all: Double
    )

    @Keep
    data class Sys(
        @SerializedName("type")
        val type: Double,
        @SerializedName("id")
        val id: Double,
        @SerializedName("country")
        val country: String,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double
    )
}