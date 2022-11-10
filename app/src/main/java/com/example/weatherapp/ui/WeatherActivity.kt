package com.example.weatherapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MessageEvent
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityWeatherBinding
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.eventMessage.observe(this) { onEvent(it) }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun onEvent(event: MessageEvent) {
        when (event) {
            MessageEvent.ShowEmptyCity -> showToast(R.string.message_empty_city)
            MessageEvent.ShowLoadingCity -> showToast(R.string.message_loading_city)
            MessageEvent.ShowFindCity -> showToast(R.string.message_find_city)
            MessageEvent.ShowNoFindCity -> showToast(R.string.message_no_find_city)
            MessageEvent.ShowErrorFindCity -> showToast(R.string.message_error_find_city)
            MessageEvent.ShowNoData -> showToast(R.string.message_no_data)
            MessageEvent.ShowNoMoreTime -> showToast(R.string.message_no_more_time)
        }
    }

    private fun showToast(message: Int) {
        Toast.makeText(
            this,
            getString(message),
            Toast.LENGTH_SHORT
        ).show()
    }
}