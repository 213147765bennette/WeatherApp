package com.example.weatherapp.ui.forecast

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.local.db.DatabaseService
import com.example.weatherapp.repository.RoomRepository
import com.example.weatherapp.ui.home.HomeViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
class MoreForecastInfoViewModel(application: Application): AndroidViewModel(application) {

    companion object{
        private val TAG = "MoreForecastInfoViewModel"
    }

    private val roomRepository = RoomRepository(DatabaseService.getInstance(application))
    //using compositeDisposable so that i avoid multiple threads making may calls while others are still active
    private val compositeDisposable = CompositeDisposable()


    //Room database variables
    val forecastId: MutableLiveData<Long> = MutableLiveData()
    val temperature: MutableLiveData<String> = MutableLiveData()
    val temp_min: MutableLiveData<String> = MutableLiveData()
    val temp_max: MutableLiveData<String> = MutableLiveData()
    val main_descr: MutableLiveData<String> = MutableLiveData()
    val full_description: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val icon: MutableLiveData<String> = MutableLiveData()


    @SuppressLint("LongLogTag")
    fun insertForecast(){
        compositeDisposable.add(
            roomRepository.insert(createInsertForecastData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                         Log.d(TAG,"Saved forecast weather data to favourites: $it")

                        isSuccess.value = true

                    },{
                        Log.e(TAG,"Error saving forecast weather to favourites!!")

                        isError.value = it.localizedMessage
                    }
                )
        )
    }

    fun createInsertForecastData(): FiveforecastEntity {

        return FiveforecastEntity(
            temperature = temperature.toString(),
            temp_min =  temp_min.toString(),
            temp_max = temp_max.toString(),
            main = main_descr.toString(),
            description = full_description.toString(),
            icon = icon.toString()

        )
    }

}