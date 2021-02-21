package com.moon.moonweather.navigation

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


@Module
class NavigationModule {

    @Provides
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}