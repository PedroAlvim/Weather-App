package com.example.weatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentInfoBinding
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel = WeatherViewModel(WeatherRepository())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        viewModel.infoHour.observe(viewLifecycleOwner) {
            binding.hour.text = it
        }

        viewModel.loadData()
    }
}