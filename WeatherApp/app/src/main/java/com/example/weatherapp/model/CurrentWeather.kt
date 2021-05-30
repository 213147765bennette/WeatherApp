package com.example.weatherapp.model

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
class CurrentWeather {

    var min:String=""
    var max:String=""
    var currentTemp:String =""
    var mainDescription:String = ""
    var latitude:String=""
    var longitude:String =""
    var currentTime:String =""

    constructor(): super()

    constructor(
        min: String,
        max: String,
        currentTemp: String,
        mainDescription: String,
        latitude: String,
        longitude: String,
        currentTime: String
    ) {
        this.min = min
        this.max = max
        this.currentTemp = currentTemp
        this.mainDescription = mainDescription
        this.latitude = latitude
        this.longitude = longitude
        this.currentTime = currentTime
    }


    override fun toString(): String {
        return "CurrentWeather(min='$min', max='$max', currentTemp='$currentTemp', mainDescription='$mainDescription')"
    }



}