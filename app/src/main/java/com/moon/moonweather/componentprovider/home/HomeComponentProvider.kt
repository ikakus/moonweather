package com.moon.moonweather.componentprovider.home

import com.moon.moonweather.componentprovider.main.MainComponentProvider
import com.moon.moonweather.core.di.DaggerComponentProvider

object HomeComponentProvider : DaggerComponentProvider<HomeComponent>() {

    override fun buildComponent(): HomeComponent {
        return DaggerHomeComponent.builder()
            .mainComponent(MainComponentProvider.dependAndGet(this))
            .build()
    }
}
