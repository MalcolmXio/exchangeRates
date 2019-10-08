package com.exchangeRates.core.ui

import com.exchangeRates.core.exception.Failure
import com.exchangeRates.core.functional.Either
import com.exchangeRates.core.platform.NetworkHandler
import com.exchangeRates.core.ui.api.RatesEntity
import com.exchangeRates.core.ui.api.RatesService
import com.exchangeRates.core.ui.rates.RatesInfo
import retrofit2.Call
import javax.inject.Inject

interface RatesRepository {

    fun rates(): Either<Failure, List<RatesInfo>>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: RatesService
    ) : RatesRepository {

        override fun rates(): Either<Failure, List<RatesInfo>> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.ratesData(),
                    { it.toCurrencyList() },
                    RatesEntity.empty()
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
    }

}