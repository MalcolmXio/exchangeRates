package com.exchangeRates.core.ui.rates

import com.exchangeRates.core.interactor.UseCase
import com.exchangeRates.core.ui.RatesRepository
import javax.inject.Inject

class GetRates
@Inject constructor(private val ratesRepository: RatesRepository) :
    UseCase<List<RatesInfo>, UseCase.None>() {

    override suspend fun run(params: None) = ratesRepository.rates()
}