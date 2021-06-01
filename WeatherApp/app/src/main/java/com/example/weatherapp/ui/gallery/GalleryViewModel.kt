package com.example.weatherapp.ui.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.local.db.DatabaseService
import com.example.weatherapp.repository.RoomRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private val TAG = "GalleryViewModel"
    }

    private val roomRepository = RoomRepository(DatabaseService.getInstance(application))
    //using compositeDisposable so that i avoid multiple threads making may calls while others are still active
    private val compositeDisposable = CompositeDisposable()


    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val isDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val forecastId : MutableLiveData<Long> = MutableLiveData()
    val temperature: MutableLiveData<String> = MutableLiveData()
    val temp_min: MutableLiveData<String> = MutableLiveData()
    val temp_max: MutableLiveData<String> = MutableLiveData()
    val main: MutableLiveData<String> = MutableLiveData()
    val date: MutableLiveData<String> = MutableLiveData()
    val week_day: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val icon: MutableLiveData<String> = MutableLiveData()
    val favouratesList : MutableLiveData<List<FiveforecastEntity>> = MutableLiveData()

    //To be included
    val city_name: MutableLiveData<String> = MutableLiveData()





    init {
        getAllStudents()
    }

    private fun getAllStudents(){
        //call the progressbar first
        isLoading.value = true

        compositeDisposable.add(
            roomRepository.getFavourates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG,"Get all favourates is called: $it")
                        favouratesList.value = it

                        isLoading.value = false
                    },
                    {
                        Log.d(TAG,"Delete is called: $it")
                        isLoading.value = false

                        //here show the error message
                        isError.value = it.localizedMessage
                    }
                )
        )
    }

    fun delete(){
        //call the progressbar first
        isLoading.value = true

        compositeDisposable.add(
            roomRepository.deleteFaourates(createDeleteForecastEntity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG,"Delete is called: $it")
                        //kill progressbar here
                        isLoading.value = false

                        //here call the success respond of delete
                        isDeleted.value = true
                    },
                    {
                        Log.e(TAG,"onError is called: $it")
                        isDeleted.value = false

                        //here show the error message
                        isError.value = it.localizedMessage
                    }
                )
        )
    }




    private fun createDeleteForecastEntity(): FiveforecastEntity{
        return FiveforecastEntity(
            id = forecastId.value!!,
            temperature = temperature.value.toString(),
            temp_min =  temp_min.value.toString(),
            temp_max = temp_max.value.toString(),
            main = main.value.toString(),
            description = description.value.toString(),
            icon = icon.value.toString(),
            city_name =  city_name.value.toString(),
            date = date.value.toString(),
            week_day =  week_day.value.toString()
        )
    }




    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}