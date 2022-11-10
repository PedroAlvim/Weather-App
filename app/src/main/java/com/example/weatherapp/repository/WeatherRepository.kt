package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.webclient.AuthenticationKey
import com.example.weatherapp.webclient.RetrofitInit

class WeatherRepository {
    companion object {
        private const val LANG = "pt"
    }

    suspend fun getWeatherFromCity2(city: String): WeatherModel? {
        return RetrofitInit().hourlyWeatherService.weatherForecast2(
            city,
            AuthenticationKey.KEY,
            LANG,
        )
    }
}