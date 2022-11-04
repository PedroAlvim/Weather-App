package com.example.weatherapp.webclient

import com.example.weatherapp.webclient.services.HourlyWeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherbit.io/v2.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val hourlyWeatherService: HourlyWeatherService = retrofit.create(HourlyWeatherService::class.java)
}