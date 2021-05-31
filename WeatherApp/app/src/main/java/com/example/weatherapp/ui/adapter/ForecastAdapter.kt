package com.example.weatherapp.ui.adapter

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MoreForecastInfo
import com.example.weatherapp.R
import com.example.weatherapp.`interface`.RecycleViewItemClickInterface
import com.example.weatherapp.response.FiveForecastResponse
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * created by {Bennette Molepo} on {5/28/2021}.
 */
class ForecastAdapter(private val forecast: List<FiveForecastResponse.Cod>,var clickInterface: RecycleViewItemClickInterface): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    companion object{
        private val TAG = "ForecastAdapter"
    }



    //inflating the layout that will be shown to the user
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

        holder.bind(forecast[position],clickInterface)


    }


    //getting the size of my list
    override fun getItemCount(): Int = forecast.size


    class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),RecycleViewItemClickInterface{

        //lateinit var recycleViewItemClickInterface:RecycleViewItemClickInterface
        val weekDay = itemView.findViewById<TextView>(R.id.txt_weekdays)
        private val weather = itemView.findViewById<TextView>(R.id.img_forecast)
        private val temperature = itemView.findViewById<TextView>(R.id.txt_temperature)





        //here am assigning the returned values to all relevent fields
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: FiveForecastResponse.Cod, action:RecycleViewItemClickInterface){

            Log.d(TAG,"BINDING_DATA $data")

            //here will loop 5 times beacuse of the 5 days forecast data to be shown
          /*  weekDay.text = data.list.get(0).dt.toString()

            weather.text = data.list.get(0).weather.get(0).main
            // weather.setImageResource(R.drawable.clear)
            temperature.text = data.list.get(0).main.temp.toString()*/

            var dateTimeText:String = getWeekdays(data.dtTxt)
            weekDay.text = dateTimeText

            weather.text = data.weather.get(0).main
            // weather.setImageResource(R.drawable.clear)
            var covertedTemp:String = convertToOneDegit(data.main.temp)
            temperature.text = covertedTemp +"\u2103"


            //recycleViewItemClickInterface = recycleViewItemClickInterface
            //this will listen when the view is clicked and perform action
            //itemView.setOnClickListener(this)

            itemView.setOnClickListener {
               action.onItemClicked(data,adapterPosition)
            }

            /*weekDay.setOnClickListener {
                Log.d(TAG,"ADPTER_MORE_DETAILS")

            }*/


        }

        fun convertToOneDegit(digtValue:Double):String{

            var decimalFormat: DecimalFormat = DecimalFormat("0")
            var formatedVal = decimalFormat.format(digtValue)

            return formatedVal
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getWeekdays(digtValue: String):String{

            var inputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            var outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)

            return LocalDate.parse(digtValue,inputFormat).format(outputFormat)
        }

        override fun onItemClicked(data: FiveForecastResponse.Cod, position: Int) {
            TODO("Not yet implemented")
        }


    }




}