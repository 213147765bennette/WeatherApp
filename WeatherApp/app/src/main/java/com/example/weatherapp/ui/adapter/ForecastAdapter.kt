package com.example.weatherapp.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.response.FiveForecastResponse
import com.google.gson.Gson
import java.text.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*


/**
 * created by {Bennette Molepo} on {5/28/2021}.
 */

class ForecastAdapter(var forecast: List<FiveForecastResponse.Cod>, var clicklisner:RecycleViewItemClickInterface): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    companion object{
        private val TAG = "ForecastAdapter"
    }



    //inflating the layout that will be shown to the user
    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fivedays_forecast_listview,parent,false)
        return ForecastViewHolder(view)
    }

    //A smart way of updating my recycleview, avoided using the notifydatachaged
    private val diffUtil = object : DiffUtil.ItemCallback<FiveForecastResponse>() {

        override fun areItemsTheSame(
            oldItem: FiveForecastResponse,
            newItem: FiveForecastResponse
        ): Boolean {
           return  oldItem.list.equals(newItem.list)
        }

        override fun areContentsTheSame(
            oldItem: FiveForecastResponse,
            newItem: FiveForecastResponse
        ): Boolean {
           return oldItem.list == newItem.list
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtil)


     fun setList(weatherList: List<FiveForecastResponse?>) {
          asyncListDiffer.submitList(weatherList)
     }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        holder.bind(forecast.get(position),clicklisner)

    }


    //getting the size of my list
    override fun getItemCount(): Int = forecast.size


    class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //lateinit var recycleViewItemClickInterface:RecycleViewItemClickInterface
        val weekDay = itemView.findViewById<TextView>(R.id.txt_weekdays)
        private val weatherIcon = itemView.findViewById<ImageView>(R.id.img_forecast)
        private val temperature = itemView.findViewById<TextView>(R.id.txt_temperature)


        //here am assigning the returned values to all relevent fields

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: FiveForecastResponse.Cod, action:RecycleViewItemClickInterface){


            Log.d(TAG,"BINDING_DATA $data")

             var dateTimeText:String = getWeekdays(data.dtTxt)
             weekDay.text = dateTimeText

            //will use the returned text to set weather icon:
            var iconText: String? = data.weather.get(0).main

            if(iconText.equals("Clear")){
                //set the clear icon
                weatherIcon.setImageResource(R.drawable.clear)

                //set ton rain icon for testing favourites icons
                //inflate the cloudy layout

            }else if(iconText.equals("Rain")){
                //set the rain icon
                weatherIcon.setImageResource(R.drawable.rain)

                //inflate the cloudy layout

            }else if (iconText.equals("Sunny")){
                //set the sunny icon
                weatherIcon.setImageResource(R.drawable.partlysunny)


                //inflate the cloudy layout
            }

            Log.d(TAG,"ICON_TEXT: $iconText")


            //will have to inflate different layout based on the weather main value




            //weatherIcon.text = data.weather.get(0).main
            // weather.setImageResource(R.drawable.clear)
            var covertedTemp:String = convertToOneDegit(data.main.temp)
            temperature.text = covertedTemp +"\u2103"


            itemView.setOnClickListener {
               action.onItemClick(data,adapterPosition)
            }


        }

        fun convertToOneDegit(digtValue:Double):String{
            var locale:Locale = Locale("en","UK")
            var pattern:String = "##"

            var decimalFormat:DecimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
            decimalFormat.applyPattern(pattern)

            return decimalFormat.format(digtValue)

          /*  var locale = Locale.getDefault()
            var decimalFormatSymbols = DecimalFormatSymbols(locale)
            var decimalFormat = DecimalFormat("0",decimalFormatSymbols)
            var formatedVal = decimalFormat.format(digtValue)

            return formatedVal*/
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun getWeekdays(digtValue: String):String{

                Log.d(TAG,"BEFORE: $digtValue")

                var simpleDateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var myDate: Date

                myDate = simpleDateformat.parse(digtValue)

                Log.d(TAG,"MYDATE: $myDate")

                var cal:Calendar = GregorianCalendar()
                cal.time =myDate

                //addind my extra 21 hours here to my current recived date time

               // cal.add(Calendar.HOUR_OF_DAY,24)

                Log.d(TAG,"AFTER: ${cal.time}")

                val formatter: DateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)

                return formatter.format(cal.time)
/*

            var inputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            var outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)

            return LocalDate.parse(digtValue,inputFormat).format(outputFormat)*/
        }


    }

    //the interface that will help me to move to another activity
    interface RecycleViewItemClickInterface {

        //this will help me to mgo to another activity to view more details
        fun onItemClick(data: FiveForecastResponse.Cod, position:Int)

    }



}