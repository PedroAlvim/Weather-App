package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.hide()
        setContentView(R.layout.activity_weather)
    }
}