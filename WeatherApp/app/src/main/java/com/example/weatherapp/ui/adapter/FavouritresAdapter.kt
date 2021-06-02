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
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.weatherapp.R
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.response.FiveForecastResponse
import com.google.android.material.imageview.ShapeableImageView
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * created by {Bennette Molepo} on {6/1/2021}.
 */
class FavouritresAdapter(
                private val deleteClickListner: (data: FiveforecastEntity) -> Unit
            ):RecyclerView.Adapter<FavouritresAdapter.FavouritesViewHolder>() {

    companion object{
        private val TAG = "FavouritresAdapter"
    }

    //A smart way of updating my recycleview
    private val diffUtil = object : DiffUtil.ItemCallback<FiveforecastEntity>() {

        override fun areItemsTheSame(oldItem: FiveforecastEntity, newItem: FiveforecastEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FiveforecastEntity, newItem: FiveforecastEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtil)

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun setList(newItem: List<FiveforecastEntity>){
        asyncListDiffer.submitList(newItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourates_forecast_listview,parent,false)

        return FavouritesViewHolder(view,deleteClickListner)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])

    }

    class FavouritesViewHolder(itemView: View, private val deleteClickListner: (data: FiveforecastEntity) -> Unit)
        : RecyclerView.ViewHolder(itemView){

        private val txtTemperature = itemView.findViewById<TextView>(R.id.txtTemp_value)
        private val txtTempMin = itemView.findViewById<TextView>(R.id.txtTempMin)
        private val txtTempMax = itemView.findViewById<TextView>(R.id.txtTempMax)
        private val txtMainDescr = itemView.findViewById<TextView>(R.id.txtMain)
        private val txtFullDescr = itemView.findViewById<TextView>(R.id.txtDescription)
        private val weatherIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
        private val txtDate = itemView.findViewById<TextView>(R.id.txtDate)
        private val txtFeelsLike = itemView.findViewById<TextView>(R.id.txtPressure)
        private val txtPressure = itemView.findViewById<TextView>(R.id.txtFells)

        //delete button
        private val deleteButton = itemView.findViewById<ShapeableImageView>(R.id.fab_favourites)


        //CITY
        private val txtCityName = itemView.findViewById<TextView>(R.id.txtDate)
        //here am assigning the returned values to all relevent fields

        @SuppressLint("SetTextI18n")
        fun bind(data: FiveforecastEntity){

            Log.d(TAG,"BINDING_DATA $data")

            txtTemperature.text = data.temperature
            txtTempMin.text = data.temp_min
            txtTempMax.text = data.temp_max
            txtMainDescr.text = data.main
            txtFullDescr.text = data.description

            txtDate.text = data.date
            txtPressure.text = data.pressure
            txtFeelsLike.text = data.feelsLike



            //will use the returned text to set weather icon:
            var iconText: String? = data.icon
            Log.d(TAG,"ICON_TEXT: $iconText")

            if(iconText.equals("Clear")){
                //set the clear icon
                weatherIcon.setImageResource(R.drawable.clear)

                //inflate the cloudy layout

            }else if(iconText.equals("Rain")){
                //set the rain icon
                weatherIcon.setImageResource(R.drawable.rain)

                //inflate the cloudy layout

            }else if (iconText.equals("Sun")){
                //set the sunny icon
                weatherIcon.setImageResource(R.drawable.partlysunny)

            }

            deleteButton.setOnClickListener {
                deleteClickListner(data)
            }


        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getWeekdays(digtValue: String):String{

            var inputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            var outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE ", Locale.ENGLISH)

            return LocalDate.parse(digtValue,inputFormat).format(outputFormat)
        }

    }


}