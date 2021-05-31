package com.example.weatherapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MoreForecastInfo
import com.example.weatherapp.R
import com.example.weatherapp.`interface`.RecycleViewItemClickInterface
import com.example.weatherapp.model.FiveDaysForecast
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.ui.adapter.ForecastAdapter
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.util.*


class HomeFragment : Fragment() ,RecycleViewItemClickInterface{

    companion object{
        private val TAG = "HomeFragment"
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var forecastAdapter: ForecastAdapter
    //private var fiveForecastResponse:List<FiveForecastResponse> = listOf()
    private var fiveForecastResponse:List<FiveForecastResponse.Cod> = listOf()
    private var forecast:List<FiveDaysForecast> = listOf<FiveDaysForecast>()
    private lateinit var fivedayForecast: FiveDaysForecast
    //private lateinit var recylerView: RecyclerView

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        val textView: TextView = root.findViewById(R.id.text_home)
        //temperature, description,temp_min,temp_max
        val txtBigCurrentTemp: TextView = root.findViewById(R.id.txtCurrent_big_temp)
        val txtDescription: TextView = root.findViewById(R.id.txxMain_description)
        val txtTempMin: TextView = root.findViewById(R.id.txt_min_value)
        val txtSmallCurrentTemp: TextView = root.findViewById(R.id.txt_current_value)
        val txtTempMax: TextView = root.findViewById(R.id.txt_max_value)

        val recyclerView: RecyclerView = root.findViewById(R.id.rv_fivedays_forecast)

        //fiveForecastResponse = ArrayList()
        fiveForecastResponse = ArrayList()
        //var forecast:List<FiveForecastResponse> = listOf(fiveForecastResponse)

        forecastAdapter = ForecastAdapter(fiveForecastResponse,this)


        //used to observe the top current temperature
       homeViewModel.currentTopWeatherLive.observe(viewLifecycleOwner, Observer {

           var bigTemperature: String = convertToOneDegit(it.main.temp)
           txtBigCurrentTemp.text = bigTemperature + "\u2103"

           txtDescription.text = it.weather.get(0).main

           var tempMin: String = convertToOneDegit(it.main.tempMin)
           txtTempMin.text = tempMin +"\u2103"

           var tempSmall: String = convertToOneDegit(it.main.temp)
           txtSmallCurrentTemp.text = tempSmall + "\u2103"

           var tempMaxi: String = convertToOneDegit(it.main.tempMax)
           txtTempMax.text = tempMaxi + "\u2103"

           Log.d(TAG,"VIEW_CURRENT: $it")

        })


        //getting live five days forecast data and setting them on my adapter
        homeViewModel.fivedaysForcastWeatherLive.observe(viewLifecycleOwner, Observer {

                val fiveForecastResponse: FiveForecastResponse = it
                val forecast:List<FiveForecastResponse> = listOf(fiveForecastResponse)

                var fivedaysList:List<FiveForecastResponse.Cod> = it.list

                Log.d(TAG,"VIEW_MODEL_CAST_DATA $fivedaysList")

                //forecastAdapter = ForecastAdapter(forecast)

                forecastAdapter = ForecastAdapter(fivedaysList,this)


                forecastAdapter.setList(forecast)


                //inflating the customadapter
                recyclerView.apply {
                    layoutManager = linearLayoutManager
                    adapter = forecastAdapter
                }

                //here making sure that the found record is displayed at the very first top
                recyclerView.post {
                    recyclerView.scrollToPosition(0)

                }

        })

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        //used to get the 5 days Forecast weather results



        return root
    }


    //using this function to convert the temperature to one decimal value
    fun convertToOneDegit(digtValue:Double):String{

        var decimalFormat:DecimalFormat = DecimalFormat("0")
        var formatedVal = decimalFormat.format(digtValue)

       return formatedVal
    }



    override fun onItemClicked(data: FiveForecastResponse.Cod, position: Int) {
        Log.d(TAG,"HAPPY_AM_CLIKED_IN_FRAGMENT")

        val intent = Intent(activity,MoreForecastInfo::class.java)
        startActivity(intent)
    }


}