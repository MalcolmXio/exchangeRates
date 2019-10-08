package com.exchangeRates.core.ui.rates.ratesFragment.adapter

import android.os.Parcel
import com.exchangeRates.core.platform.KParcelable
import com.exchangeRates.core.platform.parcelableCreator

data class RateView(
    var id: String,
    var numCode: String,
    var charCode: String,
    var nominal: Int,
    var name: String,
    var value: Float
) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::RateView)
    }

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readFloat()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
            writeString(numCode)
            writeString(charCode)
            writeInt(nominal)
            writeString(name)
            writeFloat(value)
        }
    }
}