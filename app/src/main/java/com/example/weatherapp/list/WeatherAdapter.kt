package com.example.weatherapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.WeatherForecastModel
import com.example.weatherapp.databinding.WheatherCardLayoutBinding

class WeatherAdapter(private val list: List<WeatherForecastModel>) : RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WeatherViewHolder {

        val binding =
            WheatherCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.setInfoOnViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}