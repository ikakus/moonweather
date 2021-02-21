package com.moon.moonweather.componentprovider.forecast

import com.moon.moonweather.componentprovider.main.MainComponent
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.forecast.ForecastFragment
import dagger.Component

@Component(
    dependencies = [MainComponent::class]
)
@Screen
interface ForecastComponent {
    fun inject(mainActivity: ForecastFragment)
}
