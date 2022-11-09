package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.list.WeatherAdapter
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.viewmodel.WeatherViewModel

class ForecastFragment : Fragment(R.layout.fragment_forecast) {

    private lateinit var binding: FragmentForecastBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    private var adapter: WeatherAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)

        initObservers()

        binding.searchBtn.setOnClickListener {
            val city: String = binding.searchCity.text.toString()
            viewModel.getWeather(city)
        }

        binding.cloud.setOnClickListener {
            goToInfoFragment()
        }
    }

    private fun initObservers() {
        viewModel.titleCity.observe(viewLifecycleOwner) { binding.titleCity.text = it }
        viewModel.mainHour.observe(viewLifecycleOwner) { binding.mainHour.text = it }
        viewModel.mainClime.observe(viewLifecycleOwner) {
            binding.mainClime.text = getString(R.string.temp, it)
        }
        viewModel.mainDescription.observe(viewLifecycleOwner) { binding.mainDescription.text = it }
        viewModel.listWeather.observe(viewLifecycleOwner) { initList(it) }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initList(weatherForecast: List<WeatherForecastModel>) {
        adapter = WeatherAdapter(weatherForecast)

        val recyclerView: RecyclerView = binding.horizontalWeather
        recyclerView.adapter = adapter
    }

    private fun goToInfoFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, InfoFragment())
            .addToBackStack(null)
            .commit()
    }
}