package com.example.weatherapp.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
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
            hideKeyboard()
            viewModel.getWeather(city)
        }

        binding.searchCity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId and EditorInfo.IME_MASK_ACTION != 0) {
                val city: String = binding.searchCity.text.toString()
                viewModel.getWeather(city)
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun initObservers() {
        viewModel.titleCity.observe(viewLifecycleOwner) { binding.titleCity.text = it }
        viewModel.mainHour.observe(viewLifecycleOwner) { binding.mainHour.text = it }
        viewModel.mainClime.observe(viewLifecycleOwner) {
            binding.mainClime.text = getString(R.string.temp, it)
        }
        viewModel.mainDescription.observe(viewLifecycleOwner) { binding.mainDescription.text = it }
        viewModel.listWeather.observe(viewLifecycleOwner) {
            binding.cloud.setOnClickListener {
                goToInfoFragment()
            }

            initList(it)
        }
    }

    private fun initList(weatherForecast: List<WeatherForecastModel>) {
        adapter = WeatherAdapter(weatherForecast)

        val recyclerView: RecyclerView = binding.horizontalWeather
        recyclerView.adapter = adapter
    }

    private fun goToInfoFragment() {
        findNavController().navigate(ActionOnlyNavDirections(R.id.ToInfoFragment))
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(requireActivity())
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}