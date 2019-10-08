package com.exchangeRates.features

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.exchangeRates.UnitTest
import com.exchangeRates.core.exception.Failure
import com.exchangeRates.core.ext.empty
import com.exchangeRates.core.functional.Either
import com.exchangeRates.core.platform.NetworkHandler
import com.exchangeRates.core.ui.RatesRepository
import com.exchangeRates.core.ui.api.*
import com.exchangeRates.core.ui.rates.RatesInfo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class RatesRepositoryTest : UnitTest() {

    private lateinit var networkRepository: RatesRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: RatesService

    @Mock
    private lateinit var ratesCall: Call<RatesEntity>
    @Mock
    private lateinit var ratesResponse: Response<RatesEntity>

    @Before
    fun setUp() {
        networkRepository = RatesRepository.Network(networkHandler, service)
    }

    @Test
    fun `should return empty object by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { ratesResponse.body() }.willReturn(null)
        given { ratesResponse.isSuccessful }.willReturn(true)
        given { ratesCall.execute() }.willReturn(ratesResponse)
        given { service.ratesData(any(), any(), any()) }.willReturn(
            ratesCall
        )

        val weather = networkRepository.rates(String.empty())

        weather shouldEqual Either.Right(emptyList<RatesInfo>())
        verify(service).ratesData(any(), any(), any())
    }

    @Test
    fun `should get weather from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { ratesResponse.body() }.willReturn(
            RatesEntity(
                1,
                123F,
                18,
                listOf(RatesInfo(123456L)),
                City()
            )
        )
        given { ratesResponse.isSuccessful }.willReturn(true)
        given { ratesCall.execute() }.willReturn(ratesResponse)
        given { service.ratesData(any(), any(), any()) }.willReturn(
            ratesCall
        )

        val weather = networkRepository.rates(String.empty())

        weather shouldEqual Either.Right(
            listOf(
                RatesInfo(
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
        verify(service).ratesData(any(), any(), any())
    }

    @Test
    fun `weather service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val weather = networkRepository.rates(String.empty())

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

        val weather = networkRepository.rates(String.empty())

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

        val weather = networkRepository.rates(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

    @Test
    fun `weather request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val weather = networkRepository.rates(String.empty())

        weather shouldBeInstanceOf Either::class.java
        weather.isLeft shouldEqual true
        weather.either(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

}