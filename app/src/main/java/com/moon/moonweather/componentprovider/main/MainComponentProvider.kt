package com.moon.moonweather.componentprovider.main

import com.moon.moonweather.componentprovider.app.AppComponentProvider
import com.moon.moonweather.core.di.DaggerComponentProvider

object MainComponentProvider : DaggerComponentProvider<MainComponent>() {

    override fun buildComponent(): MainComponent {
        return DaggerMainComponent.builder()
            .appComponent(AppComponentProvider.dependAndGet(this))
            .build()
    }
}
