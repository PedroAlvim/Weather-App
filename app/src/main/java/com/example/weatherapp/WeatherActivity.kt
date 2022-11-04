package com.example.weatherapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.databinding.ActivityWeatherBinding
import com.example.weatherapp.webclient.RetrofitInit
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "8be55720f96f41c0a3e3435ec23f86e0"
        private const val LANG = "pt"
    }

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var searchCity: TextInputEditText
    private lateinit var searchBtn: ImageButton
    private lateinit var titleCity: TextView
    private lateinit var mainHour: TextView
    private lateinit var mainTemp: TextView
    private lateinit var mainDescription: TextView
    private var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        supportActionBar!!.hide()
        setContentView(binding.root)
        componentInit()

        searchBtn.setOnClickListener {
            val city: String = searchCity.text.toString()

            if (city.isBlank()) {
                titleCity.setText(R.string.blank_city)
            } else {
                Toast.makeText(this, "Buscando Cidade", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch(IO) {
                    val call: Call<JsonObject> =
                        RetrofitInit().hourlyWeatherService.weatherForecast(
                            city,
                            KEY,
                            LANG,
                        )
                    val response = call.execute()
                    response.body()?.let { jsonObject ->
                        val jsonElement = jsonObject.get("data")
                        val weatherListType =
                            object : TypeToken<List<WeatherForecastModel?>?>() {}.type

                        val weatherForecast: List<WeatherForecastModel> =
                            gson.fromJson(jsonElement, weatherListType)

                        withContext(Main) {
                            titleCity.text = jsonObject.get("city_name").asString
                            mainHour.text = timeConverter(weatherForecast[0].time.toString())
                            mainTemp.text = weatherForecast[0].temp.toString()+"°" //TODO Retira esse warning
                            mainDescription.text =
                                weatherForecast[0].weather?.description.toString()
                            Toast.makeText(
                                this@WeatherActivity,
                                "Previsão encontrada com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun componentInit() {
        searchCity = binding.searchCity
        searchBtn = binding.searchBtn
        titleCity = binding.titleCity
        mainHour = binding.mainHour
        mainTemp = binding.mainClime
        mainDescription = binding.mainDescription
    }

    private fun timeConverter(time: String): String {
        val outputFormat: DateFormat = SimpleDateFormat("HH 'h'", Locale.getDefault())
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        val date: Date = inputFormat.parse(time)!!
        return outputFormat.format(date)
    }
}