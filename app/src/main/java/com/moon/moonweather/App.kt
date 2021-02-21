package com.moon.moonweather

import android.app.Application
import com.moon.moonweather.componentprovider.app.AppComponentProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponentProvider.context = this
        AppComponentProvider.get().inject(this)
    }
}
