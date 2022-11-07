package com.example.weatherapp.list

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Utils
import com.example.weatherapp.WeatherForecastModel
import com.example.weatherapp.databinding.WheatherCardLayoutBinding

class WeatherViewHolder(private val binding: WheatherCardLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setInfoOnViews(weatherPosition: WeatherForecastModel) {
        binding.hour.text = Utils().timeConverter(weatherPosition.time.toString())
        binding.clime.text = binding.root.context.getString(R.string.temp, weatherPosition.temp.toString())
        binding.description.text = weatherPosition.weather.description
    }
}