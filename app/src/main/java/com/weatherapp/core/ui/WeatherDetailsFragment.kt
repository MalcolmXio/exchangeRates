package com.weatherapp.core.ui

import android.os.Bundle
import android.view.View
import com.weatherapp.R
import com.weatherapp.core.ext.getWeatherIconUrl
import com.weatherapp.core.ext.loadUrlAndPostponeEnterTransition
import com.weatherapp.core.platform.BaseFragment
import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherView
import kotlinx.android.synthetic.main.fragment_weather_data.*
import kotlinx.android.synthetic.main.fragment_weather_data.view.*
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment() {

    @Inject
    lateinit var weatherDetailsAnimator: WeatherDetailsAnimator

    override fun layoutId() = R.layout.fragment_weather_data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        weatherDetailsAnimator.postponeEnterTransition(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewData = arguments?.getParcelable<WeatherView>(PARAM_WEATHER)
        val nameData = arguments?.getString(PARAM_TRANSITION_NAME)
        viewData?.let {
            view.weatherIcon.transitionName = nameData
            view.weatherIcon.loadUrlAndPostponeEnterTransition(
                String.getWeatherIconUrl(it),
                requireActivity()
            )
        }
        startPostponedEnterTransition()
        renderWeatherDetails(view, viewData)
    }

    override fun onBackPressed() {
        weatherDetailsAnimator.fadeInvisible(scrollView, detailsLayout)
    }

    private fun renderWeatherDetails(view: View, weatherView: WeatherView?) {
        weatherView?.let {
            with(weatherView) {
                view.countryDesc.text = country
                view.coordDesc.text = String.format(
                    getString(R.string.coord_desc),
                    lat,
                    lon
                )
                view.weatherDesc.text = weatherDescription
                view.tempDesc.text = String.format(
                    getString(R.string.tempDesc),
                    currentTemp,
                    minTemp,
                    maxTemp
                )
                view.humidityDesc.text =
                    String.format(getString(R.string.hum_desc), humidity.toInt())
                view.pressureDesc.text =
                    String.format(getString(R.string.pressure_desc), pressure.toInt())
                view.windDesc.text = String.format(
                    getString(R.string.wind_desc),
                    windSpeed,
                    windDegree
                )
            }
        }
        weatherDetailsAnimator.fadeVisible(scrollView, detailsLayout)
    }

    companion object {

        private const val PARAM_WEATHER = "param_weather"
        private const val PARAM_TRANSITION_NAME = "param_transition_name"

        fun forData(weather: WeatherView, transitionName: String): WeatherDetailsFragment {
            val weatherDetailsFragment = WeatherDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_WEATHER, weather)
            arguments.putString(PARAM_TRANSITION_NAME, transitionName)
            weatherDetailsFragment.arguments = arguments
            return weatherDetailsFragment
        }
    }

}