package com.exchangeRates.core.ui.rates.ratesFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
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
import kotlinx.android.synthetic.main.fragment_rates.view.*
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
            observe(rate, ::renderRates)
            observe(amount, ::updateRates)
            failure(failure, ::handleFailure)
        }

        exitTransition = TransitionInflater.from(activity)
            .inflateTransition(R.transition.changetransform)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener(view.amount)
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

    private fun renderRates(rate: List<RateView>?) {
        currentDataTimestamp = System.currentTimeMillis()
        ratesAdapter.collection = rate.orEmpty()
        hideProgress()
    }

    private fun updateRates(amount: Float?) {
        ratesAdapter.amount = amount ?: 1F
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

    private fun addListener(editText: AppCompatEditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    ratesViewModel.updateAmount(1F)
                } else {
                    ratesViewModel.updateAmount(s.toString().toFloat())
                }
            }

        })
    }

    companion object {
        private const val ARG_TEN_MINUTES = 600000L
    }
}