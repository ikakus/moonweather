package com.moon.moonweather.componentprovider.home

import com.moon.moonweather.componentprovider.main.MainComponent
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.home.HomeFragment
import dagger.Component

@Component(
    dependencies = [MainComponent::class]
)
@Screen
interface HomeComponent {
    fun inject(mainActivity: HomeFragment)
}
