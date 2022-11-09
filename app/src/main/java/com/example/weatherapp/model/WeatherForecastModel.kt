package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

class WeatherForecastModel(

    @SerializedName("timestamp_local")
    var time: String?,
    var temp: String?,
    @SerializedName("app_temp")
    var AppTemp: String?,
    var pop: String?,
    var precip: String?,
    var rh: String?,
    var clouds: String?,
    var weather: WeatherDescriptionModel

)