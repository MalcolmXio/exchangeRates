package com.exchangeRates.features

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.exchangeRates.UnitTest
import com.exchangeRates.core.ext.empty
import com.exchangeRates.core.functional.Either
import com.exchangeRates.core.ui.RatesRepository
import com.exchangeRates.core.ui.rates.GetRates
import com.exchangeRates.core.ui.rates.RatesInfo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetRatesTest : UnitTest() {

    private lateinit var getRates: GetRates

    @Mock
    private lateinit var ratesRepository: RatesRepository

    @Before
    fun setUp() {
        getRates = GetRates(ratesRepository)
        given { ratesRepository.rates(String.empty()) }.willReturn(
            Either.Right(
                listOf(
                    RatesInfo.empty()
                )
            )
        )
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getRates.run(String.empty()) }

        verify(ratesRepository).rates(String.empty())
        verifyNoMoreInteractions(ratesRepository)
    }
}