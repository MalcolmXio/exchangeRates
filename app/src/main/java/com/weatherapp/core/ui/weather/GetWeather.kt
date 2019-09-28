package com.weatherapp.core.ui.weather

import com.weatherapp.core.interactor.UseCase
import com.weatherapp.core.ui.WeatherRepository
import javax.inject.Inject

class GetWeather
@Inject constructor(private val weatherRepository: WeatherRepository) :
    UseCase<List<WeatherInfo>, String>() {

    override suspend fun run(params: String) = weatherRepository.weather(params)
}