package com.moon.moonweather.componentprovider.forecast

import com.moon.moonweather.componentprovider.main.MainComponentProvider
import com.moon.moonweather.core.di.DaggerComponentProvider
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object ForecastComponentProvider : DaggerComponentProvider<ForecastComponent>() {

    override fun buildComponent(): ForecastComponent {
        return DaggerForecastComponent.builder()
            .mainComponent(MainComponentProvider.dependAndGet(this))
            .build()
    }
}
