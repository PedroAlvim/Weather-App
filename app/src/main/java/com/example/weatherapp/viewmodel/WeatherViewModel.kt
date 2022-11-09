package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message //TODO EVENT AQUI

    private var _listWeather = MutableLiveData<List<WeatherForecastModel>>()
    val listWeather: LiveData<List<WeatherForecastModel>> get() = _listWeather

    private var _infoWeatherForecast = MutableLiveData<WeatherForecastModel>()
    val infoWeatherForecast: LiveData<WeatherForecastModel> get() = _infoWeatherForecast

    private var weatherModel: WeatherModel? = null

    private var infoIndex: Int = 0

    fun getWeather(city: String) {
        if (city.isEmpty()) {
            _message.postValue("Favor preencha uma cidade")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue("Buscando cidade")
            try {
                weatherModel = weatherRepository.getWeatherFromCity(city)

                weatherModel?.let { weather ->
                    _titleCity.postValue(weather.city.toString())
                    _mainHour.postValue(Utils.timeConverter(weather.data?.get(0)?.time.toString()))
                    _mainClime.postValue(weather.data?.get(0)?.temp.toString())
                    _mainDescription.postValue(weather.data?.get(0)?.weather?.description.toString())
                    weather.data.let {
                        _listWeather.postValue(it)
                    }
                    _message.postValue("Cidade encontrada")
                }

                if (weatherModel == null) {
                    _message.postValue("Não foi possivel encontrar a cidade informada")
                    return@launch
                }

            } catch (e: Exception) {
                _message.postValue("Erro ao tentar buscar cidade: $e")
            }
        }
    }

    fun loadData() {
        //TODO SE FOR NULO ACABA O BTN
        infoIndex = 0
        weatherModel?.let {
            _infoWeatherForecast.postValue(it.data?.get(0))
        } ?: _message.postValue("Não há dados a serem exibidos")
    }

    fun nextForecast() {
        weatherModel?.let {
            if (infoIndex < it.data!!.size - 1) {
                infoIndex++
            } else {
                _message.postValue("Não há mais horarios a serem exibidos")
            }
            _infoWeatherForecast.postValue(it.data?.get(infoIndex))
        } ?: _message.postValue("Não há dados a serem exibidos")
    }

    fun previousForecast() {
        weatherModel?.let {
            if (infoIndex > 0) {
                infoIndex--
            } else {
                _message.postValue("Não há mais horarios a serem exibidos")
            }
            _infoWeatherForecast.postValue(it.data?.get(infoIndex))
        } ?: _message.postValue("Não há dados a serem exibidos")
    }
}