package com.exchangeRates

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.exchangeRates.core.di.AppComponent
import com.exchangeRates.core.di.AppModule
import com.exchangeRates.core.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }

}