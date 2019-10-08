package com.exchangeRates.core.ui.api

import com.exchangeRates.core.ext.empty
import com.exchangeRates.core.ui.rates.RatesInfo
import com.google.gson.annotations.SerializedName

data class RatesEntity(
    @SerializedName("Date")
    var date: String,

    @SerializedName("Valute")
    var valute: List<Currency>
) {
    fun toCurrencyList() = valute.map {
        RatesInfo(it.id, it.numCode, it.charCode, it.nominal, it.name, it.value)
    }

    companion object {
        fun empty() = RatesEntity(
            String.empty(),
            emptyList()
        )
    }
}

data class Currency(
    @SerializedName("ID")
    var id: String = String.empty(),

    @SerializedName("NumCode")
    var numCode: String = String.empty(),

    @SerializedName("CharCode")
    var charCode: String = String.empty(),

    @SerializedName("Nominal")
    var nominal: Int = 0,

    @SerializedName("Name")
    var name: String = String.empty(),

    @SerializedName("Value")
    var value: Float = 0.toFloat(),

    @SerializedName("Previous")
    var previous: Float = 0.toFloat()
)