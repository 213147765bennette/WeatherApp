package com.example.weatherapp.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * created by {Bennette Molepo} on {5/28/2021}.
 */
/*class FiveDaysForecast(weekDay:String, weather:String, temperature:String) {

     var weekDay:String=""
     var weather:String=""
     var temperature:String=""



}*/

@Keep
data class FiveDaysForecast(
    @SerializedName("weekDay")
    val weekDay: String,
    @SerializedName("weather")
    val weather: String,
    @SerializedName("temperature")
    val temperature: String

)