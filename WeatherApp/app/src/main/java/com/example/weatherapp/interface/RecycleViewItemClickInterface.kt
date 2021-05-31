package com.example.weatherapp.`interface`

import com.example.weatherapp.response.FiveForecastResponse

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
interface RecycleViewItemClickInterface {

    //this will help me to mgo to another activity to view more details
   fun onItemClicked(data: FiveForecastResponse.Cod,position:Int)

}