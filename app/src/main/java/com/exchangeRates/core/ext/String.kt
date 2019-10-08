package com.exchangeRates.core.ext

import com.exchangeRates.core.ui.rates.ratesFragment.adapter.RateView

fun String.Companion.empty() = ""

fun String.Companion.getCountryIconUrl(view: RateView): String {
    return StringBuilder("http://openweathermap.org/img/wn/")
        .append(view.id)
        .append("@2x.png")
        .toString()
}
