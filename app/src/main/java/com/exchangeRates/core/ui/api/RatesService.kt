package com.exchangeRates.core.ui.api

import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesService
@Inject constructor(retrofit: Retrofit) : RatesApi {

    private val ratesApi by lazy { retrofit.create(RatesApi::class.java) }

    override fun ratesData(): Call<RatesEntity> {
        return ratesApi.ratesData()
    }

}