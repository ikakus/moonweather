package com.moon.moonweather.componentprovider.forecast

import com.moon.moonweather.componentprovider.main.MainComponent
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.forecast.ForecastFragment
import com.moon.moonweather.feature.forecast.ForecastModule
import dagger.Component

@Component(
    modules = [ForecastModule::class],
    dependencies = [MainComponent::class]
)
@Screen
interface ForecastComponent {
    fun inject(fragment: ForecastFragment)
}
