package com.weatherapp.core.ui.weather

import androidx.lifecycle.MutableLiveData
import com.weatherapp.core.platform.BaseViewModel
import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherView
import javax.inject.Inject

class WeatherViewModel
@Inject constructor(private val getWeather: GetWeather) : BaseViewModel() {

    var weather: MutableLiveData<List<WeatherView>> = MutableLiveData()

    fun loadWeather(cityId: String) =
        getWeather(cityId) { it.either(::handleFailure, ::handleWeatherData) }

    private fun handleWeatherData(weatherInfoList: List<WeatherInfo>) {
        this.weather.value = weatherInfoList.map {
            WeatherView(
                it.weather[0].id,
                it.dt,
                it.main.temp,
                it.main.tempMin,
                it.main.tempMax,
                it.main.pressure,
                it.main.seaLevel,
                it.main.grndLevel,
                it.weather[0].description,
                it.wind.speed,
                it.wind.deg,
                it.main.humidity,
                it.weather[0].main,
                it.weather[0].icon,
                it.city.name,
                it.city.coord.lat,
                it.city.coord.lon,
                it.city.country,
                it.city.sunrise,
                it.city.sunset
            )
        }
    }
}