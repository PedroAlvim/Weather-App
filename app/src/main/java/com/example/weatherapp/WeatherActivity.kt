package com.example.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ActivityWeatherBinding
import com.example.weatherapp.list.WeatherAdapter
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel = WeatherViewModel(WeatherRepository())
    private var adapter: WeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        supportActionBar!!.hide()
        setContentView(binding.root)

        observers()

        binding.searchBtn.setOnClickListener {
            val city: String = binding.searchCity.text.toString()
            viewModel.getWeather(city)
        }
    }

    private fun observers() {
        viewModel.titleCity.observe(this) { binding.titleCity.text = it }
        viewModel.mainHour.observe(this) { binding.mainHour.text = it }
        viewModel.mainClime.observe(this) { binding.mainClime.text = getString(R.string.temp, it) }
        viewModel.mainDescription.observe(this) { binding.mainDescription.text = it }
        viewModel.listWeather.observe(this) { initList(it) }
        viewModel.message.observe(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }

    private fun initList(weatherForecast: List<WeatherForecastModel>) {
        adapter = WeatherAdapter(weatherForecast)

        val recyclerView: RecyclerView = binding.horizontalWeather
        recyclerView.adapter = adapter
    }
}