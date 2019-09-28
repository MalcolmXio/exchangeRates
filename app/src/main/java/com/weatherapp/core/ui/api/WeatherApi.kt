package com.weatherapp.core.ui.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherApi {

    @GET("forecast")
    fun weatherData(@Query("id") cityId: String, @Query("units") units: String, @Query("appId") appId: String): Call<WeatherEntity>

}