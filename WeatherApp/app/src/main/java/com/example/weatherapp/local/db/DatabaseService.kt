package com.example.weatherapp.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.entity.FiveforecastEntity
import com.example.weatherapp.local.dao.FiveForecastDataDao

/**
 * created by {Bennette Molepo} on {5/31/2021}.
 */
@Database(entities = [FiveforecastEntity::class], version = 1,exportSchema = false)
abstract class DatabaseService: RoomDatabase() {

 abstract fun fiveForecastDataDao():FiveForecastDataDao

  companion object{

    @Volatile
    private var INSTANCE:DatabaseService? = null

    fun getInstance(context: Context):DatabaseService{

      synchronized(this){
       var instance = INSTANCE

       if(instance == null){
         instance = Room.databaseBuilder(
          context.applicationContext,
          DatabaseService::class.java,
          "forecast_demo"
         )
          .fallbackToDestructiveMigration()
          .build()

          INSTANCE = instance
       }

       return instance

      }

    }
  }
}