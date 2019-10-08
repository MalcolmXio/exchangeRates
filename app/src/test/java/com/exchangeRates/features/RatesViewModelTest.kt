package com.exchangeRates.features

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.exchangeRates.AndroidTest
import com.exchangeRates.core.ext.empty
import com.exchangeRates.core.functional.Either
import com.exchangeRates.core.ui.rates.GetRates
import com.exchangeRates.core.ui.rates.RatesInfo
import com.exchangeRates.core.ui.rates.RatesViewModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class RatesViewModelTest : AndroidTest() {

    private lateinit var ratesViewModel: RatesViewModel

    @Mock
    private lateinit var getRates: GetRates

    @Before
    fun setUp() {
        ratesViewModel = RatesViewModel(getRates)
    }

    @Test
    fun `loading weather should update live data`() {
        val weatherList = listOf(
            RatesInfo(
                Main(),
                listOf(Weather()),
                Clouds(),
                Wind(),
                Snow(),
                123456789L,
                City()
            ),
            RatesInfo(
                Main(),
                listOf(Weather()),
                Clouds(),
                Wind(),
                Snow(),
                987654321L,
                City()
            )
        )
        given { runBlocking { getRates.run(eq(String.empty())) } }.willReturn(
            Either.Right(
                weatherList
            )
        )

        ratesViewModel.rate.observeForever {
            it!!.size shouldEqualTo 2
            it[0].date shouldEqualTo 123456789L
            it[1].date shouldEqualTo 987654321L
        }

        runBlocking { ratesViewModel.loadRates(String.empty()) }
    }
}