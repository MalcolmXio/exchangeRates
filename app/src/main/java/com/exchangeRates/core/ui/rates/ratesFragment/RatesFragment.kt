package com.exchangeRates.core.ui.rates.ratesFragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.exchangeRates.R
import com.exchangeRates.core.exception.Failure
import com.exchangeRates.core.ext.*
import com.exchangeRates.core.navigation.Navigator
import com.exchangeRates.core.platform.BaseFragment
import com.exchangeRates.core.ui.RatesFailure
import com.exchangeRates.core.ui.rates.RatesViewModel
import com.exchangeRates.core.ui.rates.ratesFragment.adapter.RateView
import com.exchangeRates.core.ui.rates.ratesFragment.adapter.RatesAdapter
import kotlinx.android.synthetic.main.fragment_rates.*
import javax.inject.Inject

class RatesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var ratesAdapter: RatesAdapter

    private lateinit var ratesViewModel: RatesViewModel

    private var currentDataTimestamp = 0L

    override fun layoutId() = R.layout.fragment_rates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        ratesViewModel = viewModel(viewModelFactory) {
            observe(rate, ::renderWeather)
            failure(failure, ::handleFailure)
        }

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
        ratesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ratesList.adapter = ratesAdapter
    }

    private fun loadWeatherData() {
        ratesList.visible()
        showProgress()
        ratesViewModel.loadRates()
    }

    private fun renderWeather(rate: List<RateView>?) {
        currentDataTimestamp = System.currentTimeMillis()
        ratesAdapter.collection = rate.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        currentDataTimestamp = 0L
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is RatesFailure.DataNotAvailable -> renderFailure(R.string.failure_weather_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        ratesList.invisible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadWeatherData)
    }

    private fun checkDataTimestamp(): Boolean =
        currentDataTimestamp < (System.currentTimeMillis() - ARG_TEN_MINUTES)

    companion object {
        private const val ARG_TEN_MINUTES = 600000L
    }
}