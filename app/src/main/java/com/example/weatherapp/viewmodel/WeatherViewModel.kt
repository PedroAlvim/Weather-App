package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.MessageEvent
import com.example.weatherapp.SingleLiveEvent
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()

    private var _titleCity = MutableLiveData<String>()
    val titleCity: LiveData<String> get() = _titleCity

    private var _mainHour = MutableLiveData<String>()
    val mainHour: LiveData<String> get() = _mainHour

    private var _mainClime = MutableLiveData<String>()
    val mainClime: LiveData<String> get() = _mainClime

    private var _mainDescription = MutableLiveData<String>()
    val mainDescription: LiveData<String> get() = _mainDescription

    private var _eventMessage = SingleLiveEvent<MessageEvent>()
    val eventMessage: LiveData<MessageEvent> get() = _eventMessage

    private var _listWeather = MutableLiveData<List<WeatherForecastModel>>()
    val listWeather: LiveData<List<WeatherForecastModel>> get() = _listWeather

    private var _infoWeatherForecast = MutableLiveData<WeatherForecastModel>()
    val infoWeatherForecast: LiveData<WeatherForecastModel> get() = _infoWeatherForecast

    private var weatherModel: WeatherModel? = null

    private var infoIndex: Int = 0

    fun getWeather(city: String) {
        if (city.isEmpty()) {
            _eventMessage.postValue(MessageEvent.ShowEmptyCity)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _eventMessage.postValue(MessageEvent.ShowLoadingCity)
            try {
                weatherModel = weatherRepository.getWeatherFromCity2(city)

                weatherModel?.let { weather ->
                    _titleCity.postValue(weather.city.toString())
                    _mainHour.postValue(Utils.timeConverter(weather.data?.get(0)?.time.toString()))
                    _mainClime.postValue(weather.data?.get(0)?.temp.toString())
                    _mainDescription.postValue(weather.data?.get(0)?.weather?.description.toString())
                    weather.data.let {
                        _listWeather.postValue(it)
                    }
                    _eventMessage.postValue(MessageEvent.ShowFindCity)
                } ?: _eventMessage.postValue(MessageEvent.ShowNoFindCity)

            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Failed to load city", e)
                _eventMessage.postValue(MessageEvent.ShowErrorFindCity)
            }
        }
    }

    fun loadData() {
        infoIndex = 0
        weatherModel?.let {
            _infoWeatherForecast.postValue(it.data?.get(0))
        } ?: _eventMessage.postValue(MessageEvent.ShowNoData)
    }

    fun nextForecast() {
        weatherModel?.let {
            if (infoIndex < it.data!!.size - 1) {
                infoIndex++
            } else {
                _eventMessage.postValue(MessageEvent.ShowNoMoreTime)
            }
            _infoWeatherForecast.postValue(it.data?.get(infoIndex))
        } ?: _eventMessage.postValue(MessageEvent.ShowNoData)
    }

    fun previousForecast() {
        weatherModel?.let {
            if (infoIndex > 0) {
                infoIndex--
            } else {
                _eventMessage.postValue(MessageEvent.ShowNoMoreTime)
            }
            _infoWeatherForecast.postValue(it.data?.get(infoIndex))
        } ?: _eventMessage.postValue(MessageEvent.ShowNoData)
    }
}