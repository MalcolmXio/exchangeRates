package com.weatherapp.core.ui.weather

import android.content.Context
import android.content.Intent
import com.weatherapp.core.platform.BaseActivity
import com.weatherapp.core.ui.weather.weatherFragment.WeatherFragment

class WeatherActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, WeatherActivity::class.java)
    }

    override fun showStartFragment() = WeatherFragment()
}