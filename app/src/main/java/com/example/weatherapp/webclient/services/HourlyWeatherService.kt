package com.example.weatherapp.webclient.services

import com.example.weatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HourlyWeatherService {
    @GET("forecast/hourly")
    suspend fun weatherForecast2(
        @Query("city") city: String,
        @Query("key") key: String,
        @Query("lang") lang: String,
    ): WeatherModel?
}