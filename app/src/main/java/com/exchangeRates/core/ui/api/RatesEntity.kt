package com.exchangeRates.core.ui.api

import com.exchangeRates.core.ext.empty
import com.exchangeRates.core.ui.rates.RatesInfo
import com.google.gson.annotations.SerializedName

data class RatesEntity(
    @SerializedName("Date")
    var date: String,

    @SerializedName("Valute")
    var valute: ValutesList
) {
    fun toCurrencyList(): List<RatesInfo> {
        return listOf(
            toRatesInfo(valute.aud),
            toRatesInfo(valute.azn),
            toRatesInfo(valute.gbp),
            toRatesInfo(valute.amd),
            toRatesInfo(valute.byn),
            toRatesInfo(valute.bgn),
            toRatesInfo(valute.brl),
            toRatesInfo(valute.huf),
            toRatesInfo(valute.hkd),
            toRatesInfo(valute.dkk),
            toRatesInfo(valute.usd),
            toRatesInfo(valute.eur),
            toRatesInfo(valute.inr),
            toRatesInfo(valute.kzt),
            toRatesInfo(valute.cad),
            toRatesInfo(valute.kgs),
            toRatesInfo(valute.cny),
            toRatesInfo(valute.mdl),
            toRatesInfo(valute.nok),
            toRatesInfo(valute.pln),
            toRatesInfo(valute.ron),
            toRatesInfo(valute.xdr),
            toRatesInfo(valute.sgd),
            toRatesInfo(valute.tjs),
            toRatesInfo(valute.tury),
            toRatesInfo(valute.tmt),
            toRatesInfo(valute.uzs),
            toRatesInfo(valute.uah),
            toRatesInfo(valute.czk),
            toRatesInfo(valute.sek),
            toRatesInfo(valute.chf),
            toRatesInfo(valute.zar),
            toRatesInfo(valute.krw),
            toRatesInfo(valute.jpy)
        )

    }

    private fun toRatesInfo(currency: Currency): RatesInfo {
        return RatesInfo(
            currency.id,
            currency.numCode,
            currency.charCode,
            currency.nominal,
            currency.name,
            currency.value
        )
    }

    companion object {
        fun empty() = RatesEntity(
            String.empty(),
            ValutesList()
        )
    }
}

data class ValutesList(
    @SerializedName("AUD")
    var aud: Currency = Currency(),

    @SerializedName("AZN")
    var azn: Currency = Currency(),

    @SerializedName("GBP")
    var gbp: Currency = Currency(),

    @SerializedName("AMD")
    var amd: Currency = Currency(),

    @SerializedName("BYN")
    var byn: Currency = Currency(),

    @SerializedName("BGN")
    var bgn: Currency = Currency(),

    @SerializedName("BRL")
    var brl: Currency = Currency(),

    @SerializedName("HUF")
    var huf: Currency = Currency(),

    @SerializedName("HKD")
    var hkd: Currency = Currency(),

    @SerializedName("DKK")
    var dkk: Currency = Currency(),

    @SerializedName("USD")
    var usd: Currency = Currency(),

    @SerializedName("EUR")
    var eur: Currency = Currency(),

    @SerializedName("INR")
    var inr: Currency = Currency(),

    @SerializedName("KZT")
    var kzt: Currency = Currency(),

    @SerializedName("CAD")
    var cad: Currency = Currency(),

    @SerializedName("KGS")
    var kgs: Currency = Currency(),

    @SerializedName("CNY")
    var cny: Currency = Currency(),

    @SerializedName("MDL")
    var mdl: Currency = Currency(),

    @SerializedName("NOK")
    var nok: Currency = Currency(),

    @SerializedName("PLN")
    var pln: Currency = Currency(),

    @SerializedName("RON")
    var ron: Currency = Currency(),

    @SerializedName("XDR")
    var xdr: Currency = Currency(),

    @SerializedName("SGD")
    var sgd: Currency = Currency(),

    @SerializedName("TJS")
    var tjs: Currency = Currency(),

    @SerializedName("TRY")
    var tury: Currency = Currency(),

    @SerializedName("TMT")
    var tmt: Currency = Currency(),

    @SerializedName("UZS")
    var uzs: Currency = Currency(),

    @SerializedName("UAH")
    var uah: Currency = Currency(),

    @SerializedName("CZK")
    var czk: Currency = Currency(),

    @SerializedName("SEK")
    var sek: Currency = Currency(),

    @SerializedName("CHF")
    var chf: Currency = Currency(),

    @SerializedName("ZAR")
    var zar: Currency = Currency(),

    @SerializedName("KRW")
    var krw: Currency = Currency(),

    @SerializedName("JPY")
    var jpy: Currency = Currency()
)

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