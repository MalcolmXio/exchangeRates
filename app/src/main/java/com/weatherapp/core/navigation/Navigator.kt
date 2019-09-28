package com.weatherapp.core.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.weatherapp.core.platform.BaseActivity
import com.weatherapp.core.ui.WeatherDetailsFragment
import com.weatherapp.core.ui.weather.WeatherActivity
import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showWeather(context)
    }

    fun showDetails(activity: BaseActivity, weather: WeatherView, navigationExtras: Extras) {
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val fragment = WeatherDetailsFragment.forData(weather, sharedView.transitionName)
        activity.addNextFragment(fragment, sharedView)
    }

    private fun showWeather(context: Context) =
        context.startActivity(WeatherActivity.callingIntent(context))

    class Extras(val transitionSharedElement: View)
}