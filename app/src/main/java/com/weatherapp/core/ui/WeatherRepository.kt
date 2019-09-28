package com.weatherapp.core.ui

import com.weatherapp.core.exception.Failure
import com.weatherapp.core.functional.Either
import com.weatherapp.core.platform.NetworkHandler
import com.weatherapp.core.ui.api.WeatherEntity
import com.weatherapp.core.ui.api.WeatherService
import com.weatherapp.core.ui.weather.WeatherInfo
import retrofit2.Call
import javax.inject.Inject

interface WeatherRepository {

    fun weather(cityId: String): Either<Failure, List<WeatherInfo>>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: WeatherService
    ) : WeatherRepository {

        override fun weather(cityId: String): Either<Failure, List<WeatherInfo>> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.weatherData(cityId, METRIC_SYSTEM, APP_ID),
                    { it.toWeatherInfoList() },
                    WeatherEntity.empty()
                )
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }

        companion object {
            // OpenWeatherMap key
            private const val APP_ID = "2d7fc2fdf99cc6c1128058efcb74cc2c"
            // Parameters format. "metric" provides temperature in Celsius
            private const val METRIC_SYSTEM = "metric"
        }
    }
}