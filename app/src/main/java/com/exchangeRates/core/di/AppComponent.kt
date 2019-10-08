package com.exchangeRates.core.di

import com.exchangeRates.App
import com.exchangeRates.core.di.viewModel.ViewModelModule
import com.exchangeRates.core.navigation.RouteActivity
import com.exchangeRates.core.ui.rates.ratesFragment.RatesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: App)
    fun inject(routeActivity: RouteActivity)

    fun inject(ratesFragment: RatesFragment)
}