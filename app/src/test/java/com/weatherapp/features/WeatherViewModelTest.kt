package com.weatherapp.features

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.weatherapp.AndroidTest
import com.weatherapp.core.ext.empty
import com.weatherapp.core.functional.Either
import com.weatherapp.core.ui.api.*
import com.weatherapp.core.ui.weather.GetWeather
import com.weatherapp.core.ui.weather.WeatherInfo
import com.weatherapp.core.ui.weather.WeatherViewModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class WeatherViewModelTest : AndroidTest() {

    private lateinit var weatherViewModel: WeatherViewModel

    @Mock
    private lateinit var getWeather: GetWeather

    @Before
    fun setUp() {
        weatherViewModel = WeatherViewModel(getWeather)
    }

    @Test
    fun `loading weather should update live data`() {
        val weatherList = listOf(
            WeatherInfo(
                Main(),
                listOf(Weather()),
                Clouds(),
                Wind(),
                Snow(),
                123456789L,
                City()
            ),
            WeatherInfo(
                Main(),
                listOf(Weather()),
                Clouds(),
                Wind(),
                Snow(),
                987654321L,
                City()
            )
        )
        given { runBlocking { getWeather.run(eq(String.empty())) } }.willReturn(
            Either.Right(
                weatherList
            )
        )

        weatherViewModel.weather.observeForever {
            it!!.size shouldEqualTo 2
            it[0].date shouldEqualTo 123456789L
            it[1].date shouldEqualTo 987654321L
        }

        runBlocking { weatherViewModel.loadWeather(String.empty()) }
    }
}