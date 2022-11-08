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

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private var _titleCity = MutableLiveData<String>()
    val titleCity: LiveData<String> get() = _titleCity

    private var _mainHour = MutableLiveData<String>()
    val mainHour: LiveData<String> get() = _mainHour

    private var _mainClime = MutableLiveData<String>()
    val mainClime: LiveData<String> get() = _mainClime

    private var _mainDescription = MutableLiveData<String>()
    val mainDescription: LiveData<String> get() = _mainDescription

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private var _listWeather = MutableLiveData<List<WeatherForecastModel>>()
    val listWeather: LiveData<List<WeatherForecastModel>> get() = _listWeather

    fun getWeather(city: String) {
        if(city.isEmpty()){
            _message.postValue("Favor preencha uma cidade")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _message.postValue("Buscando cidade")
            try {
                val weatherModel: WeatherModel? = weatherRepository.getWeatherFromCity(city)

                if (weatherModel != null) {
                    _titleCity.postValue(weatherModel.city.toString())
                    _mainHour.postValue(Utils.timeConverter(weatherModel.data?.get(0)?.time.toString()))
                    _mainClime.postValue(weatherModel.data?.get(0)?.temp.toString())
                    _mainDescription.postValue(weatherModel.data?.get(0)?.weather?.description.toString())
                    weatherModel.data.let {
                        _listWeather.postValue(it)
                    }
                } else {
                    _message.postValue("Não foi possivel encontrar a cidade informada")
                    return@launch
                }
                _message.postValue("Cidade encontrada")
            }catch (e: Exception){
                _message.postValue("Erro ao tentar buscar cidade: $e")
            }
        }
    }
}