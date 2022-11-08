package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.webclient.AuthenticationKey
import com.example.weatherapp.webclient.RetrofitInit
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call

class WeatherRepository {
    companion object {
        private const val LANG = "pt"
    }

    private var gson = Gson()

    fun getWeatherFromCity(city: String): WeatherModel? {
        val call: Call<JsonObject> =
            RetrofitInit().hourlyWeatherService.weatherForecast(
                city,
                AuthenticationKey.KEY,
                LANG,
            )
        val response = call.execute()

        response.body()?.let { jsonObject ->
            val weatherType = object : TypeToken<WeatherModel?>() {}.type
            return gson.fromJson<WeatherModel?>(jsonObject, weatherType)
        }
        return null
    }
}