package com.exchangeRates.core.ui.api

import retrofit2.Call
import retrofit2.http.GET

internal interface RatesApi {

    @GET("daily_json.js")
    fun ratesData(): Call<RatesEntity>

}