package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentInfoBinding
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.viewmodel.WeatherViewModel

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)

        binding.rightArrow.setOnClickListener { viewModel.nextForecast() }
        binding.leftArrow.setOnClickListener { viewModel.previousForecast() }

        viewModel.loadData()
        viewModel.infoWeatherForecast.observe(viewLifecycleOwner) { setInfo(it) }
    }

    private fun setInfo(weather: WeatherForecastModel) {
        binding.hour.text = Utils.timeConverter(weather.time.toString())
        binding.temp.text = requireActivity().getString(R.string.temp, weather.temp)
        binding.description.text = weather.weather.description
        binding.appTemp.text = requireActivity().getString(R.string.app_temp, weather.appTemp)
        binding.pop.text = requireActivity().getString(R.string.info_pop, "${weather.pop}%")
        binding.precip.text = requireActivity().getString(R.string.info_precip, weather.precip)
        binding.rh.text = requireActivity().getString(R.string.info_rh, "${weather.rh}%")
        binding.clouds.text =
            requireActivity().getString(R.string.info_clouds, "${weather.clouds}%")
    }
}