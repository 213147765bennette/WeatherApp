package com.example.weatherapp.http

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
object WeatherApis {

         //in here am declaring all the open weather apis that i will use to get my weather results

        //CURRENT WEATHER DATA
        const val TEST_CURRENT_WEATHER = "http://api.openweathermap.org/data/2.5/weather?lat=-26.0208631&lon=28.1995223&units=metric&appid=3c2c9119fd96f5765412b2eb67d81316"

         const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
         const val CURRENT_ENDPOINT = "weather?lat=-26.0208631&lon=28.1995223&units=metric&appid=3c2c9119fd96f5765412b2eb67d81316"

        //FIVE DAY FORECATS WEATHER DATA
        const val TEST_5_DAYS = "http://api.openweathermap.org/data/2.5/forecast?lat=-26.0209&lon=28.1995&appid=3c2c9119fd96f5765412b2eb67d81316"

        const val BASE_URL_2 = "http://api.openweathermap.org/data/2.5/"
        const val FORECAST_ENDPOINT = "forecast?lat=26.0208631&lon=28.1995223&appid=3c2c9119fd96f5765412b2eb67d81316"


        //with the response count of 5
        const val FORECAST_ENDPOINT_COUNT= "forecast?&lat=26.0208631&lon=28.1995223&cnt=5&appid=3c2c9119fd96f5765412b2eb67d81316"

        //http://api.openweathermap.org/data/2.5/forecast?&lat=26.0208631&lon=28.1995223&cnt=5&appid=3c2c9119fd96f5765412b2eb67d81316



        //the api used to get the current weather
        //Will remove harcoded lat and long values to use the user current LatLong values
        const val CURRENT = "http://api.openweathermap.org/data/2.5/weather?lat=-26.0208631&lon=28.1995223&units=metric&appid=3c2c9119fd96f5765412b2eb67d81316"
        const  val CURRENT_WEATHER ="http://api.openweathermap.org/data/2.5/onecall?lat=-26.0208631&lon=28.1995223&units=metric&APPID="






}