package com.example.weatherapp.webclient.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HourlyWeatherService {
    @GET("forecast/hourly")
    fun weatherForecast(
        @Query("city") city: String,
        @Query("key") key: String,
        @Query("lang") lang: String,
        @Query("hours") hours: String,
        ): Call<JsonObject>
}