package com.weatherapp.core.ext

import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherView

fun String.Companion.empty() = ""

fun String.Companion.getWeatherIconUrl(view: WeatherView): String {
    return StringBuilder("http://openweathermap.org/img/wn/")
        .append(view.iconId)
        .append("@2x.png")
        .toString()
}
