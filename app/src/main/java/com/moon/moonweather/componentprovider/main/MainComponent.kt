package com.moon.moonweather.componentprovider.main

import android.content.Context
import com.moon.moonweather.componentprovider.app.AppComponent
import com.moon.moonweather.core.di.Feature
import com.moon.moonweather.feature.main.MainActivity
import com.moon.moonweather.navigation.NavigationModule
import dagger.Component
import ru.terrakok.cicerone.Router

@Component(
    modules = [
        NavigationModule::class
    ],
    dependencies = [AppComponent::class]
)
@Feature
interface MainComponent {
    fun router(): Router
    fun context(): Context
    fun inject(mainActivity: MainActivity)
}
