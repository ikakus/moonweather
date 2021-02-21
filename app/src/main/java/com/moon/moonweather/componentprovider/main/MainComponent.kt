package com.moon.moonweather.componentprovider.main

import com.moon.moonweather.MainActivity
import com.moon.moonweather.componentprovider.app.AppComponent
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.navigation.NavigationModule
import dagger.Component

@Component(
    modules = [
        NavigationModule::class
    ],
    dependencies = [AppComponent::class]
)
@Screen
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
