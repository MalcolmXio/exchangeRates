package com.weatherapp.features

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.weatherapp.UnitTest
import com.weatherapp.core.ext.empty
import com.weatherapp.core.functional.Either
import com.weatherapp.core.ui.WeatherRepository
import com.weatherapp.core.ui.weather.GetWeather
import com.weatherapp.core.ui.weather.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetWeatherTest : UnitTest() {

    private lateinit var getWeather: GetWeather

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        getWeather = GetWeather(weatherRepository)
        given { weatherRepository.weather(String.empty()) }.willReturn(
            Either.Right(
                listOf(
                    WeatherInfo.empty()
                )
            )
        )
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getWeather.run(String.empty()) }

        verify(weatherRepository).weather(String.empty())
        verifyNoMoreInteractions(weatherRepository)
    }
}