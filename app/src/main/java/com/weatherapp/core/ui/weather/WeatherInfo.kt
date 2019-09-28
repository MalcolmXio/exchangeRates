package com.weatherapp.core.ui.weather

import com.weatherapp.core.ui.api.*

data class WeatherInfo(
    var main: Main,
    var weather: List<Weather>,
    var clouds: Clouds,
    var wind: Wind,
    var snow: Snow,
    var dt: Long,
    var city: City
) {
    companion object {
        fun empty() = WeatherInfo(
            Main(),
            emptyList(),
            Clouds(),
            Wind(),
            Snow(),
            0.toLong(),
            City()
        )
    }
}