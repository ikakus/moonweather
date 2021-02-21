package com.moon.moonweather.componentprovider.main

import com.moon.moonweather.componentprovider.app.AppComponent
import com.moon.moonweather.core.di.Feature
import com.moon.moonweather.feature.main.MainActivity
import com.moon.moonweather.navigation.NavigationModule
import dagger.Component

@Component(
    modules = [
        NavigationModule::class
    ],
    dependencies = [AppComponent::class]
)
@Feature
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
