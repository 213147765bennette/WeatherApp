package com.example.weatherapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.ui.adapter.ForecastAdapter
import com.example.weatherapp.ui.forecast.MoreForecastInfo
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
    private lateinit var cityForecastResponse: FiveForecastResponse.City
    //private lateinit var cordinatorLayout:ScrollView

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
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
        //cordinatorLayout = root.findViewById(R.id.main_layout)

        //to use to change background images and colours : layoutImage  weather_cardview recyclerView

        val layoutImage:LinearLayout = root.findViewById<LinearLayout>(R.id.weather_image)
        val weather_cardview:CardView = root.findViewById<CardView>(R.id.weather_cardview)

        val recyclerView: RecyclerView = root.findViewById(R.id.rv_fivedays_forecast)


        //fiveForecastResponse = ArrayList()
        fiveForecastResponse = ArrayList()
        //var forecast:List<FiveForecastResponse> = listOf(fiveForecastResponse)

        forecastAdapter = ForecastAdapter(fiveForecastResponse,this)


        //used to observe the top current temperature
       homeViewModel.currentTopWeatherLive.observe(viewLifecycleOwner, Observer {

           //change backgground here
           //layoutImage  weather_cardview recyclerView
           if(it.weather.get(0).main.equals("Clouds")){

               Log.d(TAG,"===========CLOUDS============")
               layoutImage.setBackgroundResource(R.drawable.sea_cloudy)
               weather_cardview.setCardBackgroundColor(Color.parseColor("#54717A"))
               recyclerView.setBackgroundColor(resources.getColor(R.color.cloudy))

           }else if (it.weather.get(0).main.equals("Clear")){

               Log.d(TAG,"===========CLEAR============")
               layoutImage.setBackgroundResource(R.drawable.sea_sunnypng)
               //weather_cardview.setCardBackgroundColor(R.color.sunny)
               recyclerView.setBackgroundColor(resources.getColor(R.color.weather_blue))
               weather_cardview.setCardBackgroundColor(Color.parseColor("#4c94e3"))

           }else if (it.weather.get(0).main.equals("Sunny")){

               Log.d(TAG,"===========CLEAR============")
               layoutImage.setBackgroundResource(R.drawable.sea_sunnypng)
               //weather_cardview.setCardBackgroundColor(R.color.sunny)
               recyclerView.setBackgroundColor(resources.getColor(R.color.weather_blue))
               weather_cardview.setCardBackgroundColor(Color.parseColor("#4c94e3"))

           }
           else if (it.weather.get(0).main.equals("Rain")){

               Log.d(TAG,"===========RAIN============")
               layoutImage.setBackgroundResource(R.drawable.sea_rainy)
               //weather_cardview.setBackgroundColor(resources.getColor(R.color.rainy) )
               recyclerView.setBackgroundColor(resources.getColor(R.color.rainy))
               weather_cardview.setCardBackgroundColor(Color.parseColor("#57575D"))

           }else{
               layoutImage.setBackgroundResource(R.drawable.sea_sunnypng)
               //weather_cardview.setCardBackgroundColor(R.color.sunny)
               recyclerView.setBackgroundColor(resources.getColor(R.color.weather_blue))
               weather_cardview.setCardBackgroundColor(Color.parseColor("#4c94e3"))
           }


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
                cityForecastResponse = fiveForecastResponse.city
                Log.d(TAG,"CITY_OBJECT: $cityForecastResponse")

                val forecast:List<FiveForecastResponse> = listOf(fiveForecastResponse)



                fivedaysList = it.list

                Log.d(TAG,"VIEW_MODEL_CAST_DATA $fivedaysList")

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


            /*}else{
                showSnackbar("Network request failed, please check the network or refresh the application.")
                //Toast.makeText(context, "Network request failed, please check the network..", Toast.LENGTH_SHORT).show()
            }*/


        })

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        //used to get the 5 days Forecast weather results


        return root
    }

    /*fun showSnackbar(msg:String){
        val mySnackbar = Snackbar.make(cordinatorLayout,msg,Snackbar.LENGTH_LONG)
        mySnackbar.show()
    }*/


    //using this function to convert the temperature to one decimal value
    fun convertToOneDegit(digtValue:Double):String{

        var locale:Locale = Locale("en","UK")
        var pattern:String = "#"

        var decimalFormat:DecimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)

        return decimalFormat.format(digtValue)

        //var decimalFormat:DecimalFormat = DecimalFormat("0")
    /*    var formatedVal = decimalFormat.format(digtValue)

       return formatedVal*/
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(data: FiveForecastResponse.Cod, position: Int) {
        Log.d(TAG,"HAPPY_AM_CLIKED_IN_FRAGMENT")

        //used toast for testing
        //Toast.makeText(context,"clods: ${data.clouds} ${data.main.temp}",Toast.LENGTH_LONG).show()

        //Toast.makeText(context,"POSITION CLICKED: ${position}",Toast.LENGTH_LONG).show()

        var dateTimeText:String = getWeekdays(data.dtTxt)


        val intent = Intent(activity, MoreForecastInfo::class.java)

        intent.putExtra("TEMPERATURE",convertToOneDegit(data.main.temp))
        intent.putExtra("TEMPMIN",convertToOneDegit(data.main.tempMin))
        intent.putExtra("TEMPMAX",convertToOneDegit(data.main.tempMax))
        intent.putExtra("MAINDESCR", data.weather[0].main)
        intent.putExtra("FULLDESCR", data.weather[0].description)
        intent.putExtra("PRESSURE", convertToOneDegit(data.main.pressure))
        intent.putExtra("FEELS", convertToOneDegit(data.main.feelsLike))
        intent.putExtra("DATE", dateTimeText)

        intent.putExtra("CITYNAME",cityForecastResponse.name)
        intent.putExtra("COUNTRYNAME",cityForecastResponse.country)

        //this two values of Lat and lon will save them to roomdb i will later need them on my map
        intent.putExtra("LAT",cityForecastResponse.coord.lat.toString())
        intent.putExtra("LON",cityForecastResponse.coord.lon.toString())

        /*  intent.putExtra("TEMPERATURE",fivedaysList.get(0).main.temp.toString())
          intent.putExtra("TEMPMIN",fivedaysList.get(0).main.tempMin.toString())
          intent.putExtra("TEMPMAX",fivedaysList.get(0).main.tempMax.toString())
          intent.putExtra("MAINDESCR", fivedaysList.get(0).weather[0].main)
          intent.putExtra("FULLDESCR", fivedaysList.get(0).weather[0].description)
          intent.putExtra("CITY_NAME", fivedaysList.get(0).weather[0].description)
          intent.putExtra("WEEKDAY", fivedaysList.get(0).weather[0].description)
          intent.putExtra("DATE", fivedaysList.get(0).weather[0].description)
  */


        startActivity(intent)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun getWeekdays(digtValue: String):String{

        var simpleDateformat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var inputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH)

        return LocalDate.parse(digtValue,inputFormat).format(outputFormat)

    }






}