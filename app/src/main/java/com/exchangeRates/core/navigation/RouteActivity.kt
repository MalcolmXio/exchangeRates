package com.exchangeRates.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exchangeRates.App
import com.exchangeRates.core.di.AppComponent
import javax.inject.Inject

class RouteActivity : AppCompatActivity() {

    private val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as App).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}