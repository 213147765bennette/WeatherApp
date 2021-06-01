package com.example.weatherapp.ui.forecast

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.local.db.DatabaseService
import com.example.weatherapp.repository.RoomRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FiveDaysForecastViewModel(application: Application) : ViewModel() {

    companion object{
        private val TAG = "FiveDaysForecastViewModel"
    }

    //using compositeDisposable so that i avoid multiple threads making may calls while others are still active
    private val compositeDisposable = CompositeDisposable()
    private val roomRepository = RoomRepository(DatabaseService.getInstance(application))

    //Room database variables
    val forecastId: MutableLiveData<Long> = MutableLiveData()
    val temperature: MutableLiveData<String> = MutableLiveData()
    val temp_min: MutableLiveData<String> = MutableLiveData()
    val temp_max: MutableLiveData<String> = MutableLiveData()
    val main_descr: MutableLiveData<String> = MutableLiveData()
    val full_description: MutableLiveData<String> = MutableLiveData()
    val icon: MutableLiveData<String> = MutableLiveData()


    fun insertForecast(){
        compositeDisposable.add(
            roomRepository.insert(createInsertForecastData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                       // Log.d(TAG,"Saved forecast weather data to favourites: $it")


                    },{
                        //Log.e(TAG,"Error saving forecast weather to favourites!!")
                    }
                )
        )
    }

    fun createInsertForecastData(): FiveforecastEntity {

        return FiveforecastEntity(
            id = forecastId.value!!,
            temperature = temperature.toString(),
            temp_min =  temp_min.toString(),
            temp_max = temp_max.toString(),
            main = main_descr.toString(),
            description = full_description.toString(),
            icon = icon.toString()

        )
    }


}