package com.example.weatherapp.ui.forecast

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import com.afollestad.materialdialogs.MaterialDialog
import com.example.weatherapp.R
import com.example.weatherapp.ui.home.HomeFragment
import com.example.weatherapp.ui.home.HomeViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class MoreForecastInfo : AppCompatActivity() {

     companion object{
         private val TAG = "MoreForecastInfo"
     }

    private lateinit var foreCastViewModel: MoreForecastInfoViewModel
    private lateinit var fabAddStudent: ExtendedFloatingActionButton
    private lateinit var txtTemperature: TextView
    private lateinit var txtTempMin: TextView
    private lateinit var txtTempMax: TextView
    private lateinit var txtMainDescr: TextView
    private lateinit var txtFullDescr: TextView
    private lateinit var imgIcon: ImageView
    private lateinit var txtDate: TextView
    private lateinit var txtPressure: TextView
    private lateinit var txtFells: TextView
    private lateinit var txtCityName: TextView
    private lateinit var txtBigTemp: TextView
    private lateinit var txtCountryName: TextView
    private lateinit var moreDetailsView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_forecast_info)
        title = "More Details"

        init()

        //here initializing my view model
        foreCastViewModel = ViewModelProvider(
            this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MoreForecastInfoViewModel::class.java)

        //calling all my dialogs to be activated
        observers()

        fabAddStudent.setOnClickListener {
            showAddFavourateDialog();

        }

    }

    @SuppressLint("SetTextI18n")
    fun init(){

        fabAddStudent = findViewById(R.id.fab_favourites)

        txtTemperature = findViewById(R.id.txtTemp_value)
        txtTempMin = findViewById(R.id.txtTempMin)
        txtTempMax = findViewById(R.id.txtTempMax)
        txtMainDescr = findViewById(R.id.txtMain)
        txtFullDescr = findViewById(R.id.txtDescription)
        imgIcon = findViewById(R.id.imgIcon)
        txtDate = findViewById(R.id.txtDate)
        txtPressure = findViewById(R.id.txtPressure)
        txtFells = findViewById(R.id.txtFells)
        txtCityName = findViewById(R.id.txtCityName)
        txtCountryName = findViewById(R.id.txtCountryName)
        txtBigTemp = findViewById(R.id.txtBigTemp)
        moreDetailsView = findViewById(R.id.main_more_info_layout)

        //now set values to show on my design
        txtTemperature.text = intent.getStringExtra("TEMPERATURE").toString() + "\u2103"
        txtTempMin.text = intent.getStringExtra("TEMPMIN").toString()+ "\u2103"
        txtTempMax.text = intent.getStringExtra("TEMPMAX").toString()+ "\u2103"
        txtMainDescr.text = intent.getStringExtra("MAINDESCR").toString()
        txtFullDescr.text = intent.getStringExtra("FULLDESCR").toString()
        txtPressure.text = intent.getStringExtra("PRESSURE").toString()+ "\u2103"
        txtFells.text = intent.getStringExtra("FEELS").toString()+ "\u2103"
        txtDate.text = intent.getStringExtra("DATE").toString()
        txtCityName.text = intent.getStringExtra("CITYNAME").toString()

/*
        var convertTemp:String = convertToOneDegit(intent.getStringExtra("TEMPERATURE")!!.toDouble())

        Log.d(TAG,"CONVERT_TEMP: $convertTemp")*/

        txtBigTemp.text = intent.getStringExtra("TEMPERATURE").toString() + "\u2103"

        //here get the full country name from the code
        //var locale = Locale("",intent.getStringExtra("COUNTRYNAME").toString())

        txtCountryName.text = getCountryOnCode(intent.getStringExtra("COUNTRYNAME").toString())



        //HERE I WANT TO SEE IF I HAVE RECEIVED MY VALUES FROM PREVOIUS ADAPTER
        var cityName:String = intent.getStringExtra("CITYNAME").toString()
        var countryName:String = intent.getStringExtra("COUNTRYNAME").toString()
        var cityLat:String = intent.getStringExtra("LAT").toString()
        var cityLon:String = intent.getStringExtra("LON").toString()

        Log.d(TAG,"CITYOBJECT: cityName: $cityName : country: $countryName : cityLat: $cityLat : cityLon: $cityLon")

        //intent.putExtra("ICON", fivedaysList.get(0).weather[0].icon)

        //will use the returned text to set weather icon:
        var iconText: String? = intent.getStringExtra("MAINDESCR").toString()


        if(iconText.equals("Clear")){
            imgIcon.setImageResource(R.drawable.clear)
            moreDetailsView.setBackgroundColor(Color.parseColor("#4c94e3"))

        }else if(iconText.equals("Rain")){
            imgIcon.setImageResource(R.drawable.rain)
            moreDetailsView.setBackgroundColor(Color.parseColor("#57575D"))

        }else if (iconText.equals("Sunny")){
            imgIcon.setImageResource(R.drawable.partlysunny)
            moreDetailsView.setBackgroundColor(Color.parseColor("#4c94e3"))

        }else if (iconText.equals("Clouds")){
            imgIcon.setImageResource(R.drawable.partlysunny)
            moreDetailsView.setBackgroundColor(Color.parseColor("#54717A"))
        }

        Log.d(TAG,"ICON_TEXT: $iconText")

        //imgIcon = intent.getStringExtra("ICON")

    }

    //in here observe live data from my model
    private fun observers(){

        foreCastViewModel.isError.observe(this, Observer {
            it?.let { msg ->{
                showOnErrorDialog(msg)
            } }
        })

        foreCastViewModel.isSuccess.observe(this, Observer {
            if(it){
                showSuccessDialog()
            }
        })

        foreCastViewModel.isDeleted.observe(this, Observer {
            if(it){
                showDeleteDialog()
            }
        })

    }

    fun getCountryOnCode(countryCode:String):String{
        var locale = Locale("",countryCode)
        return locale.getDisplayCountry()
    }

    private fun showAddFavourateDialog(){

        val dialog = MaterialDialog(this)
            .cornerRadius(8f)
            .cancelable(false)
            .title(R.string.dialog_add_title)
             .message(R.string.dialog_message)

        dialog.positiveButton(R.string.dialog_save) {

            //get values passed using my intent from home fragment and set them to my mutable live date to store on roomdb

            //now set values to show on my design
            foreCastViewModel.temperature.value = intent.getStringExtra("TEMPERATURE").toString() + "\u2103"
            foreCastViewModel.temp_min.value = intent.getStringExtra("TEMPMIN").toString()+ "\u2103"
            foreCastViewModel.temp_max.value = intent.getStringExtra("TEMPMAX").toString()+ "\u2103"
            foreCastViewModel.main_descr.value = intent.getStringExtra("MAINDESCR").toString()
            foreCastViewModel.full_description.value = intent.getStringExtra("FULLDESCR").toString()
            foreCastViewModel.icon.value = intent.getStringExtra("ICON").toString()
            foreCastViewModel.pressure.value = intent.getStringExtra("PRESSURE").toString()+ "\u2103"
            foreCastViewModel.feelsLike.value = intent.getStringExtra("FEELS").toString()+ "\u2103"
            foreCastViewModel.date.value = intent.getStringExtra("DATE").toString()
            foreCastViewModel.cityName.value = intent.getStringExtra("CITYNAME").toString()
            foreCastViewModel.countryName.value = intent.getStringExtra("COUNTRYNAME").toString()


            //now insert the data to roomdb
            foreCastViewModel.insertForecast()



            /*foreCastViewModel.temperature.value = "25"
            foreCastViewModel.temp_min.value = "-10"
            foreCastViewModel.temp_max.value = "85"
            foreCastViewModel.main_descr.value = "Sun"
            foreCastViewModel.full_description.value = "Very Sunny"
            foreCastViewModel.icon.value = "Clear"
            foreCastViewModel.city_name.value = "Tembisa"
            foreCastViewModel.week_day.value = "Saturday"
            foreCastViewModel.date.value = "21-May-2021"*/


            //here to call the method of persiting to roomdb
            //Toast.makeText(this,
                //R.string.dialog_add_message,Toast.LENGTH_LONG).show()
          // dialog.dismiss()

        }

        dialog.negativeButton {
            dialog.dismiss()
        }

        dialog.show()

    }


    //show the success dialog
    private fun showSuccessDialog(){

        val dialog = MaterialDialog(this)
                .cornerRadius(8f)
                .cancelable(false)
                .title(R.string.dialog_success_title)
                .message(R.string.dialog_success_msg)

        dialog.positiveButton {
            dialog.dismiss()


        }

        dialog.show()

    }

    private fun showOnErrorDialog(msg:String){

        val dialog = MaterialDialog(this)
                .cornerRadius(8f)
                .cancelable(false)
                .title(R.string.dialog_error_title)
                .message(null,msg)

        dialog.positiveButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    //show the deleted dialog
    private fun showDeleteDialog(){

        val dialog = MaterialDialog(this)
                .cornerRadius(8f)
                .cancelable(false)
                .title(R.string.dialog_deleted_title)
                .message(R.string.dialog_deleted_msg)

        dialog.positiveButton {
            dialog.dismiss()
        }

        dialog.show()

    }

    //using this function to convert the temperature to one decimal value
    fun convertToOneDegit(digtValue: Double?):String{


        var locale:Locale = Locale("en","UK")
        var pattern:String = "#"

        var decimalFormat:DecimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)

        return decimalFormat.format(digtValue)

  /*
        var decimalFormat: DecimalFormat = DecimalFormat("0")
        var formatedVal = decimalFormat.format(digtValue)

        return formatedVal*/
    }



}