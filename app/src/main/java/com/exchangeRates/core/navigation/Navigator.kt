package com.exchangeRates.core.navigation

import android.content.Context
import com.exchangeRates.core.ui.rates.RatesActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showRates(context)
    }

    private fun showRates(context: Context) =
        context.startActivity(RatesActivity.callingIntent(context))

}