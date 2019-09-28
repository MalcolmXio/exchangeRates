package com.weatherapp.core.ui.api

import com.google.gson.annotations.SerializedName
import com.weatherapp.core.ext.empty
import com.weatherapp.core.ui.weather.WeatherInfo

data class WeatherEntity(
    @SerializedName("cod")
    var cod: Int,

    @SerializedName("message")
    var message: Float,

    @SerializedName("cnt")
    var cnt: Int,

    @SerializedName("list")
    var list: List<WeatherData>,

    @SerializedName("city")
    var city: City
) {
    fun toWeatherInfoList() = list.map {
        WeatherInfo(it.main, it.weather, it.clouds, it.wind, it.snow, it.dt, city)
    }

    companion object {
        fun empty() = WeatherEntity(
            0,
            0.toFloat(),
            0,
            emptyList(),
            City()
        )
    }
}

data class WeatherData(
    @SerializedName("dt")
    var dt: Long = 0.toLong(),

    @SerializedName("main")
    var main: Main = Main(),

    @SerializedName("weather")
    var weather: List<Weather> = emptyList(),

    @SerializedName("clouds")
    var clouds: Clouds = Clouds(),

    @SerializedName("wind")
    var wind: Wind = Wind(),

    @SerializedName("snow")
    var snow: Snow = Snow(),

    @SerializedName("sys")
    var sys: Sys = Sys(),

    @SerializedName("dt_txt")
    var dtTxt: String = String.empty()
)

data class Main(
    @SerializedName("temp")
    var temp: Float = 0.toFloat(),

    @SerializedName("temp_min")
    var tempMin: Float = 0.toFloat(),

    @SerializedName("temp_max")
    var tempMax: Float = 0.toFloat(),

    @SerializedName("pressure")
    var pressure: Float = 0.toFloat(),

    @SerializedName("sea_level")
    var seaLevel: Float = 0.toFloat(),

    @SerializedName("grnd_level")
    var grndLevel: Float = 0.toFloat(),

    @SerializedName("humidity")
    var humidity: Float = 0.toFloat(),

    @SerializedName("temp_kf")
    var tempKf: Float = 0.toFloat()
)

data class Weather(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("main")
    var main: String = String.empty(),

    @SerializedName("description")
    var description: String = String.empty(),

    @SerializedName("icon")
    var icon: String = String.empty()
)

data class Clouds(
    @SerializedName("all")
    var all: Int = 0
)

data class Wind(
    @SerializedName("speed")
    var speed: Float = 0.toFloat(),

    @SerializedName("deg")
    var deg: Float = 0.toFloat()
)

data class Snow(
    @SerializedName("3h")
    var h: Float = 0.toFloat()
)

data class Sys(
    @SerializedName("pod")
    var pod: String = String.empty()
)

data class City(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = String.empty(),

    @SerializedName("coord")
    var coord: Coord = Coord(),

    @SerializedName("country")
    var country: String = String.empty(),

    @SerializedName("timezone")
    var timezone: Int = 0,

    @SerializedName("sunrise")
    var sunrise: Long = 0.toLong(),

    @SerializedName("sunset")
    var sunset: Long = 0.toLong()
)

data class Coord(
    @SerializedName("lat")
    var lat: Float = 0.toFloat(),

    @SerializedName("lon")
    var lon: Float = 0.toFloat()
)