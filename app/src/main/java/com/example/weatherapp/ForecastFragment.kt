package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.list.WeatherAdapter
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel

class ForecastFragment : Fragment(R.layout.fragment_forecast) {

    private lateinit var binding: FragmentForecastBinding
    private val viewModel = WeatherViewModel(WeatherRepository())
    private var adapter: WeatherAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        observers()

        binding.searchBtn.setOnClickListener {
            val city: String = binding.searchCity.text.toString()
            viewModel.getWeather(city)
        }

        binding.cloud.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()

            viewModel.goToInfo(fragmentTransaction)
        }
    }

    private fun observers() {
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
}