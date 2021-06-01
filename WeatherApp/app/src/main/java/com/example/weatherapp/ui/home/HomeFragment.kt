package com.example.weatherapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
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

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }


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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(data: FiveForecastResponse.Cod, position: Int) {
        Log.d(TAG,"HAPPY_AM_CLIKED_IN_FRAGMENT")

        //used toast for testing
        //Toast.makeText(context,"clods: ${data.clouds} ${data.main.temp}",Toast.LENGTH_LONG).show()

        //Toast.makeText(context,"POSITION CLICKED: ${position}",Toast.LENGTH_LONG).show()

        var dateTimeText:String = getWeekdays(data.dtTxt)


        val intent = Intent(activity, MoreForecastInfo::class.java)

        intent.putExtra("TEMPERATURE",data.main.temp.toString())
        intent.putExtra("TEMPMIN",data.main.tempMin.toString())
        intent.putExtra("TEMPMAX",data.main.tempMax.toString())
        intent.putExtra("MAINDESCR", data.weather[0].main)
        intent.putExtra("FULLDESCR", data.weather[0].description)
        intent.putExtra("PRESSURE", data.main.pressure.toString())
        intent.putExtra("FEELS", data.main.feelsLike.toString())
        intent.putExtra("DATE", dateTimeText)


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
    fun getWeekdays(digtValue: String):String{

        var inputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)

        return LocalDate.parse(digtValue,inputFormat).format(outputFormat)
    }





}