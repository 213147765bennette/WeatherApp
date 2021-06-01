package com.example.weatherapp.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.http.Networking
import com.example.weatherapp.http.WeatherApis
import com.example.weatherapp.local.db.DatabaseService
import com.example.weatherapp.repository.RoomRepository
import com.example.weatherapp.repository.TaskRepository
import com.example.weatherapp.response.CurrentTaskResponse
import com.example.weatherapp.response.FiveForecastResponse
import com.example.weatherapp.response.TopCurrentResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    companion object{
        private val TAG = "HomeViewModel"

    }

    //using compositeDisposable so that i avoid multiple threads making may calls while others are still active
    private val compositeDisposable = CompositeDisposable()

    //Repositories that will help me to perform all my operations
    val currentTaskRepository: TaskRepository

    val currentWeatherLive: MutableLiveData<CurrentTaskResponse> = MutableLiveData()
    val currentTopWeatherLive: MutableLiveData<TopCurrentResponse> = MutableLiveData()
    val fivedaysForcastWeatherLive: MutableLiveData<FiveForecastResponse> = MutableLiveData()


    init {

        currentTaskRepository = TaskRepository(Networking.currentWeatherRetriver(WeatherApis.BASE_URL))

        //call function to get current weather data
        getTopCurrentWeather()

        //call function to get five days forecast
        getFiveDaysForecast()
    }

    //function to get current temperature from the api
    fun getTopCurrentWeather(){
        Log.d(TAG,"ENTER_HERE")
        compositeDisposable.add(
            currentTaskRepository.getTopCurrentWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        currentTopWeatherLive.value = it
                        Log.d(TAG,"CURRENT WEATHER DATA: $it")
                    },
                    {
                        Log.d(TAG,"ERRRORO ${it.localizedMessage}")
                    }
                )
        )
    }

    fun getFiveDaysForecast(){

        Log.d(TAG,"ENTER_FORECAST")

        compositeDisposable.add(
            currentTaskRepository.getFiveDaysForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        fivedaysForcastWeatherLive.value = it
                        Log.d(TAG,"FIVE DAYS WEATHER FORECAST: $it")
                    },
                    {
                        Log.d(TAG,"ERRR ${it.localizedMessage}")
                    }
                )
        )
    }


    private val _text = MutableLiveData<String>().apply {

        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}