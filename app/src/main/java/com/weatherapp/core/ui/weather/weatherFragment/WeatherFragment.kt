package com.weatherapp.core.ui.weather.weatherFragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.core.exception.Failure
import com.weatherapp.core.ext.*
import com.weatherapp.core.navigation.Navigator
import com.weatherapp.core.platform.BaseActivity
import com.weatherapp.core.platform.BaseFragment
import com.weatherapp.core.ui.WeatherFailure
import com.weatherapp.core.ui.weather.WeatherViewModel
import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherAdapter
import com.weatherapp.core.ui.weather.weatherFragment.adapter.WeatherView
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject

class WeatherFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    private lateinit var weatherViewModel: WeatherViewModel

    private var currentDataTimestamp = 0L

    override fun layoutId() = R.layout.fragment_weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        weatherViewModel = viewModel(viewModelFactory) {
            observe(weather, ::renderWeather)
            failure(failure, ::handleFailure)
        }

        sharedElementReturnTransition = TransitionInflater.from(activity)
            .inflateTransition(R.transition.changetransform)

        exitTransition = TransitionInflater.from(activity)
            .inflateTransition(R.transition.changetransform)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(::initializeView, ::loadWeatherData)
    }


    private fun initializeView() {
        swipeRefresh.setOnRefreshListener {
            if (checkDataTimestamp()) {
                loadWeatherData()
            }
            swipeRefresh.isRefreshing = false
        }
        weatherDataList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        weatherDataList.adapter = weatherAdapter
        weatherAdapter.clickListener = { weather, navigationExtras ->
            navigator.showDetails((activity as BaseActivity), weather, navigationExtras)
        }
    }

    private fun loadWeatherData() {
        weatherDataList.visible()
        showProgress()
        weatherViewModel.loadWeather(ARG_CITY_ID)
    }

    private fun renderWeather(weather: List<WeatherView>?) {
        currentDataTimestamp = System.currentTimeMillis()
        weatherAdapter.collection = weather.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        currentDataTimestamp = 0L
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is WeatherFailure.DataNotAvailable -> renderFailure(R.string.failure_weather_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        weatherDataList.invisible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadWeatherData)
    }

    private fun checkDataTimestamp(): Boolean =
        currentDataTimestamp < (System.currentTimeMillis() - ARG_TEN_MINUTES)

    companion object {
        private const val ARG_CITY_ID = "4711647"
        private const val ARG_TEN_MINUTES = 600000L
    }
}