package com.weatherapp.core.ui.weather.weatherFragment.adapter

import android.os.Parcel
import com.weatherapp.core.platform.KParcelable
import com.weatherapp.core.platform.parcelableCreator

data class WeatherView(
    val id: Int,
    val date: Long,

    val currentTemp: Float,
    val minTemp: Float,
    val maxTemp: Float,

    val pressure: Float,
    val seaLevel: Float,
    val groundLevel: Float,

    val weatherDescription: String,

    val windSpeed: Float,
    val windDegree: Float,

    val humidity: Float,

    val skyCondition: String,
    val iconId: String,

    val cityName: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::WeatherView)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString().orEmpty(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString().orEmpty(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeLong(date)
            writeFloat(currentTemp)
            writeFloat(minTemp)
            writeFloat(maxTemp)
            writeFloat(pressure)
            writeFloat(seaLevel)
            writeFloat(groundLevel)
            writeString(weatherDescription)
            writeFloat(windSpeed)
            writeFloat(windDegree)
            writeFloat(humidity)
            writeString(skyCondition)
            writeString(iconId)
            writeString(cityName)
            writeFloat(lat)
            writeFloat(lon)
            writeString(country)
            writeLong(sunrise)
            writeLong(sunset)
        }
    }
}