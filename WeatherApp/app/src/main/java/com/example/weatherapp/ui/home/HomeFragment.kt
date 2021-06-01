package com.example.weatherapp.ui.home

import android.content.Intent
import android.location.LocationManager
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
import com.example.weatherapp.ui.forecast.MoreForecastInfo
import com.example.weatherapp.R
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.ui.adapter.ForecastAdapter
import java.text.DecimalFormat
import java.util.*


class HomeFragment : Fragment() , ForecastAdapter.RecycleViewItemClickInterface {

    companion object{
        val TAG = "HomeFragment"
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var forecastAdapter: ForecastAdapter
    //private var fiveForecastResponse:List<FiveForecastResponse> = listOf()
    private var fiveForecastResponse:List<FiveForecastResponse.Cod> = listOf()
    lateinit var fivedaysList:List<FiveForecastResponse.Cod>
    //private lateinit var recylerView: RecyclerView
    lateinit var  root:View

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


        root  = inflater.inflate(R.layout.fragment_home, container, false)


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

           if(it.weather.get(0).main.equals("Clear")){

               root  = inflater.inflate(R.layout.fragment_home_rainny, container, false)

           }else if (it.weather.get(0).main.equals("Rain")){

               Log.d(TAG,"===========RAIN============")
               root  = inflater.inflate(R.layout.fragment_home_rainny, container, false)

           }else if (it.weather.get(0).main.equals("Sun")){

               root  = inflater.inflate(R.layout.fragment_home_rainny, container, false)
           }

        })


        //getting live five days forecast data and setting them on my adapter
        homeViewModel.fivedaysForcastWeatherLive.observe(viewLifecycleOwner, Observer {

                val fiveForecastResponse: FiveForecastResponse = it
                val forecast:List<FiveForecastResponse> = listOf(fiveForecastResponse)

                fivedaysList = it.list

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


    override fun onItemClick(data: FiveForecastResponse.Cod, position: Int) {
        Log.d(TAG,"HAPPY_AM_CLIKED_IN_FRAGMENT")

        //used toast for testing
        //Toast.makeText(context,"clods: ${data.clouds} ${data.main.temp}",Toast.LENGTH_LONG).show()

        /*  to also include
          :city.name
          country name*/

        var doubleVal:Double =  fivedaysList.get(0).main.temp

        Log.d(TAG, doubleVal.toString())

        val intent = Intent(activity, MoreForecastInfo::class.java)
           intent.putExtra("TEMPERATURE",fivedaysList.get(0).main.temp)
           intent.putExtra("TEMPMIN",fivedaysList.get(0).main.tempMin)
           intent.putExtra("TEMPMAX",fivedaysList.get(0).main.tempMax)
           intent.putExtra("MAINDESCR", fivedaysList.get(0).weather[0].main)
           intent.putExtra("FULLDESCR", fivedaysList.get(0).weather[0].description)
           intent.putExtra("ICON", fivedaysList.get(0).weather[0].icon)

           /* intent.putExtra("TEMPERATURE","25")
            intent.putExtra("TEMPMIN","12")
            intent.putExtra("TEMPMAX","85")
            intent.putExtra("MAINDESCR", "Rain")
            intent.putExtra("FULLDESCR", "Shower Rain")
            intent.putExtra("ICON", "Clear")*/

        startActivity(intent)
    }





}