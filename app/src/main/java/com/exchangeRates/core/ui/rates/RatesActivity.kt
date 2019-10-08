package com.exchangeRates.core.ui.rates

import android.content.Context
import android.content.Intent
import com.exchangeRates.core.platform.BaseActivity
import com.exchangeRates.core.ui.rates.ratesFragment.RatesFragment

class RatesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, RatesActivity::class.java)
    }

    override fun showStartFragment() = RatesFragment()
}