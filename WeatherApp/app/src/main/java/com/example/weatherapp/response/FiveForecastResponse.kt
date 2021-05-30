package com.example.weatherapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FiveForecastResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Double,
    @SerializedName("cnt")
    val cnt: Double,
    @SerializedName("list")
    val list: List<Cod>,
    @SerializedName("city")
    val city: City
) {
    @Keep
    data class Cod(
        @SerializedName("dt")
        val dt: Double,
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("visibility")
        val visibility: Double,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("dt_txt")
        val dtTxt: String
    ) {
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
            @SerializedName("sea_level")
            val seaLevel: Double,
            @SerializedName("grnd_level")
            val grndLevel: Double,
            @SerializedName("humidity")
            val humidity: Double,
            @SerializedName("temp_kf")
            val tempKf: Double
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
        data class Clouds(
            @SerializedName("all")
            val all: Double
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
        data class Sys(
            @SerializedName("pod")
            val pod: String
        )
    }

    @Keep
    data class City(
        @SerializedName("id")
        val id: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("country")
        val country: String,
        @SerializedName("population")
        val population: Double,
        @SerializedName("timezone")
        val timezone: Double,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double
    ) {
        @Keep
        data class Coord(
            @SerializedName("lat")
            val lat: Double,
            @SerializedName("lon")
            val lon: Double
        )
    }
}