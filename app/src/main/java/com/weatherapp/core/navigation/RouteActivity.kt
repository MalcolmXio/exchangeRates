package com.weatherapp.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weatherapp.App
import com.weatherapp.core.di.AppComponent
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