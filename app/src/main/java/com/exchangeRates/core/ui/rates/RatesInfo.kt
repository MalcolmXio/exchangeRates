package com.exchangeRates.core.ui.rates

import com.exchangeRates.core.ext.empty

data class RatesInfo(
    var id: String,
    var numCode: String,
    var charCode: String,
    var nominal: Int,
    var name: String,
    var value: Float
) {
    companion object {
        fun empty() = RatesInfo(
            String.empty(),
            String.empty(),
            String.empty(),
            0,
            String.empty(),
            0.toFloat()
        )
    }
}