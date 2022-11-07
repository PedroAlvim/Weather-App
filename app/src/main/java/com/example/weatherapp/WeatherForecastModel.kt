package com.example.weatherapp

import com.google.gson.annotations.SerializedName

class WeatherForecastModel(

    @SerializedName("timestamp_local")
    var time: String?,
    var temp: String?,
    var weather: WeatherModel

)