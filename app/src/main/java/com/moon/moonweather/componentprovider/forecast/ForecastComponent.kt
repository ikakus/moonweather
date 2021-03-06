package com.moon.moonweather.componentprovider.forecast

import com.moon.data.NetworkModule
import com.moon.moonweather.componentprovider.main.MainComponent
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.forecast.ForecastFragment
import com.moon.moonweather.feature.forecast.di.ForecastModule
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Component(
    modules = [
        ForecastModule::class,
        NetworkModule::class,
    ],
    dependencies = [MainComponent::class]
)
@Screen
interface ForecastComponent {
    fun inject(fragment: ForecastFragment)
}
