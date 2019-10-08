package com.exchangeRates.core.ui

import com.exchangeRates.core.exception.Failure

class RatesFailure {
    class DataNotAvailable : Failure.FeatureFailure()
    class NonExistentRates : Failure.FeatureFailure()
}

