package com.exchangeRates.core.ui.rates

import androidx.lifecycle.MutableLiveData
import com.exchangeRates.core.interactor.UseCase
import com.exchangeRates.core.platform.BaseViewModel
import com.exchangeRates.core.ui.rates.ratesFragment.adapter.RateView
import javax.inject.Inject

class RatesViewModel
@Inject constructor(private val getRates: GetRates) : BaseViewModel() {

    var rate: MutableLiveData<List<RateView>> = MutableLiveData()
    var amount: MutableLiveData<Float> = MutableLiveData()

    fun updateAmount(value: Float) {
        this.amount.value = value
    }

    fun loadRates() =
        getRates(UseCase.None()) { it.either(::handleFailure, ::handleRatesData) }

    private fun handleRatesData(ratesInfoList: List<RatesInfo>) {
        this.rate.value = ratesInfoList.map {
            RateView(
                it.id,
                it.numCode,
                it.charCode,
                it.nominal,
                it.name,
                it.value
            )
        }
    }
}