package com.weatherapp.core.ui.api

import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherService
@Inject constructor(retrofit: Retrofit) : WeatherApi {

    private val weatherApi by lazy { retrofit.create(WeatherApi::class.java) }

    override fun weatherData(
        cityId: String,
        units: String,
        appId: String
    ): Call<WeatherEntity> {
        return weatherApi.weatherData(cityId, units, appId)
    }

}