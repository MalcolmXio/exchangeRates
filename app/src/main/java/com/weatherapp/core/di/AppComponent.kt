package com.weatherapp.core.di

import com.weatherapp.App
import com.weatherapp.core.di.viewModel.ViewModelModule
import com.weatherapp.core.navigation.RouteActivity
import com.weatherapp.core.ui.WeatherDetailsFragment
import com.weatherapp.core.ui.weather.weatherFragment.WeatherFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: App)
    fun inject(routeActivity: RouteActivity)

    fun inject(weatherFragment: WeatherFragment)
    fun inject(weatherDetailsFragment: WeatherDetailsFragment)
}