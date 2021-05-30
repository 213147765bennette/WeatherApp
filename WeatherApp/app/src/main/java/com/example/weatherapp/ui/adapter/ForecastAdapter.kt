package com.example.weatherapp.ui.adapter

import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.response.FiveForecastResponse

/**
 * created by {Bennette Molepo} on {5/28/2021}.
 */
class ForecastAdapter(private val forecast: List<FiveForecastResponse>): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

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



    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        for (count in  0..4){
            holder.bind(forecast[count])
        }

    }

    fun setList(weatherList: List<FiveForecastResponse?>) {
         asyncListDiffer.submitList(weatherList)
    }
    //getting the size of my list
    override fun getItemCount(): Int = forecast.size

    class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val weekDay = itemView.findViewById<TextView>(R.id.txt_weekdays)
        private val weather = itemView.findViewById<TextView>(R.id.img_forecast)
        private val temperature = itemView.findViewById<TextView>(R.id.txt_temperature)

        //here am assigning the returned values to all relevent fields
        fun bind(data: FiveForecastResponse){

            Log.d(TAG,"BINDING_DATA $data")

            //here will loop 5 times beacuse of the 5 days forecast data to be shown
            weekDay.text = data.list.get(0).dt.toString()

            weather.text = data.list.get(0).weather.get(0).main
            // weather.setImageResource(R.drawable.clear)
            temperature.text = data.list.get(0).main.temp.toString()





        }
    }


}