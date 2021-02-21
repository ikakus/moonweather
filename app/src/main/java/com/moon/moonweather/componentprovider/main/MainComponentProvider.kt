package com.moon.moonweather.componentprovider.main

import android.annotation.SuppressLint
import com.moon.moonweather.componentprovider.app.AppComponentProvider
import com.moon.moonweather.core.di.DaggerComponentProvider

@SuppressLint("StaticFieldLeak")
object MainComponentProvider : DaggerComponentProvider<MainComponent>() {

    override fun buildComponent(): MainComponent {
        return DaggerMainComponent.builder()
            .appComponent(AppComponentProvider.dependAndGet(this))
            .build()
    }
}
