package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MoreForecastInfo : AppCompatActivity() {

    private lateinit var fabAddStudent: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_forecast_info)
        title = "More Details"

        init()

        fabAddStudent.setOnClickListener {
            Toast.makeText(this,"Weather data aded to favourites",Toast.LENGTH_LONG).show()
        }
    }

    fun init(){
        fabAddStudent = findViewById(R.id.fab_favourites)
    }

}