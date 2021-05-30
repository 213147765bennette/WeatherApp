package com.example.weatherapp.http

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * created by {Bennette Molepo} on {5/29/2021}.
 */
object Networking {
    private const val NEWTWORKING_TIMEOUT = 60;
    private lateinit var okHttpClient: OkHttpClient

    fun currentWeatherRetriver(baseUrl:String): NetworkService{

        okHttpClient = OkHttpClient().newBuilder()
            .readTimeout(NEWTWORKING_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(NEWTWORKING_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }

    fun fiveForecastWeatherRetriver(baseUrl:String): NetworkService{

        okHttpClient = OkHttpClient().newBuilder()
            .readTimeout(NEWTWORKING_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(NEWTWORKING_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}