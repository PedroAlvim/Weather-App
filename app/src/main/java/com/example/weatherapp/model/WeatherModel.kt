package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

class WeatherModel(
    @SerializedName("city_name")
    var city: String?,
    var data: List<WeatherForecastModel>?
)