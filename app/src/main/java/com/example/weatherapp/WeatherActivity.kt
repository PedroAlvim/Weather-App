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
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class WeatherActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "8be55720f96f41c0a3e3435ec23f86e0"
        private const val LANG = "pt"
        private const val HOURS = "24"
    }

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var searchCity: TextInputEditText
    private lateinit var searchBtn: ImageButton
    private lateinit var titleCity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        supportActionBar!!.hide()
        setContentView(binding.root)
        componentInit()

        searchBtn.setOnClickListener {
            val city: String = searchCity.text.toString()

            if(city.isBlank()){
                titleCity.setText(R.string.blank_city)
            }else{
                Toast.makeText(this, "Buscando Cidade", Toast.LENGTH_SHORT).show()
                try{
                    lifecycleScope.launch(IO) {
                        val call: Call<JsonObject> = RetrofitInit().hourlyWeatherService.weatherForecast(city, KEY, LANG, HOURS)
                        val response = call.execute()
                        response.body()?.let {
                            val toString = it.toString()
                        }
                    }
                }catch (e: Exception){
                    Toast.makeText(this, "Erro ao buscar previsão", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun componentInit(){
        searchCity = binding.searchCity
        searchBtn = binding.searchBtn
        titleCity = binding.titleCity
    }
}