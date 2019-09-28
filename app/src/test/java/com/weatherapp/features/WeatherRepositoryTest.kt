package com.weatherapp.features

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.weatherapp.UnitTest
import com.weatherapp.core.exception.Failure
import com.weatherapp.core.ext.empty
import com.weatherapp.core.functional.Either
import com.weatherapp.core.platform.NetworkHandler
import com.weatherapp.core.ui.WeatherRepository
import com.weatherapp.core.ui.api.*
import com.weatherapp.core.ui.weather.WeatherInfo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class WeatherRepositoryTest : UnitTest() {

    private lateinit var networkRepository: WeatherRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: WeatherService

    @Mock
    private lateinit var weatherCall: Call<WeatherEntity>
    @Mock
    private lateinit var weatherResponse: Response<WeatherEntity>

    @Before
    fun setUp() {
        networkRepository = WeatherRepository.Network(networkHandler, service)
    }

    @Test
    fun `should return empty object by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { weatherResponse.body() }.willReturn(null)
        given { weatherResponse.isSuccessful }.willReturn(true)
        given { weatherCall.execute() }.willReturn(weatherResponse)
        given { service.weatherData(any(), any(), any()) }.willReturn(
            weatherCall
        )

        val weather = networkRepository.weather(String.empty())

        weather shouldEqual Either.Right(emptyList<WeatherInfo>())
        verify(service).weatherData(any(), any(), any())
    }

    @Test
    fun `should get weather from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { weatherResponse.body() }.willReturn(
            WeatherEntity(
                1,
                123F,
                18,
                listOf(WeatherData(123456L)),
                City()
            )
        )
        given { weatherResponse.isSuccessful }.willReturn(true)
        given { weatherCall.execute() }.willReturn(weatherResponse)
        given { service.weatherData(any(), any(), any()) }.willReturn(
            weatherCall
        )

        val weather = networkRepository.weather(String.empty())

        weather shouldEqual Either.Right(
            listOf(
                WeatherInfo(
                    Main(),
                    emptyList(),
                    Clouds(),
                    Wind(),
                    Snow(),
                    123456L,
                    City()
                )
            )
        )
        verify(service).weatherData(any(), any(), any())
    }

    @Test
    fun `weather service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val weather = networkRepository.weather(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `weather service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val weather = networkRepository.weather(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `weather service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val weather = networkRepository.weather(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

    @Test
    fun `weather request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val weather = networkRepository.weather(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

}