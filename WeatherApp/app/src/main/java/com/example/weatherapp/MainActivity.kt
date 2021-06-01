package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.StrictMode
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object{
        private val TAG = "MainActivity"
        var MY_PERMISSION_REQUEST_LOCATION:Int = 88
        var  latitude:Double = -26.0209
        var longitude:Double = 28.1995

    }


    var UPDATE_INTERVAL:Long = 10 * 100
    var FASTEST_INTERVAL: Long = 10 *100

    lateinit var locationRequest:LocationRequest
    lateinit var userLastlocation:Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //asking for location
        askForLocation()
        //check for GPS Here
        askforGps()

        //now get user current location
        startUserLocationUpdates()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    ///Asking for location and gps here
    fun askforGps(){

        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled: Boolean
        var network_enabled: Boolean

        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(!gps_enabled && !network_enabled){
            showGpsDialog()
        }

    }


    fun askForLocation(){
        ActivityCompat.requestPermissions(this,
       arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        MY_PERMISSION_REQUEST_LOCATION)



    }

    fun checkPermission(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ||ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),122)
        }
    }

    fun startUserLocationUpdates(){

        //first will check all my required permissions
        checkPermission()

        //here am setting all my strategies and on how should i frequently get user updated location
        locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(UPDATE_INTERVAL)
        locationRequest.setFastestInterval(FASTEST_INTERVAL)

        var builder:LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        var locationSettingsRequest: LocationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)

        var settingClient:SettingsClient = LocationServices.getSettingsClient(this)
        settingClient.checkLocationSettings(locationSettingsRequest)


        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {


                latitude = locationResult.lastLocation.latitude
                longitude = locationResult.lastLocation.longitude

                Log.d(TAG,"USER LOCATIONS: lat: $latitude :long: $longitude")

                //use toast to check results
                //Toast.makeText(this@MainActivity, "Current User Location: lat: $latitude :long: $longitude", Toast.LENGTH_LONG).show()

            }
        },
                Looper.myLooper())

    }


    //show the GPS DIALOG
    private fun showGpsDialog(){

        val dialog = MaterialDialog(this)
                .cornerRadius(8f)
                .cancelable(false)
                .title(R.string.gps_title)
                .message(R.string.gps_msg)

        dialog.positiveButton(R.string.gps_button_name) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            dialog.dismiss()

        }

        dialog.show()

    }
}