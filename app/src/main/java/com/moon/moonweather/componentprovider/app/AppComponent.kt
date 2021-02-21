package com.moon.moonweather.componentprovider.app

import android.content.Context
import com.moon.moonweather.App
import com.moon.moonweather.core.di.Application
import dagger.BindsInstance
import dagger.Component

@Component
@Application
interface AppComponent {

    fun inject(moonApp: App)

    fun context(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun provideContext(context: Context): Builder

        fun build(): AppComponent
    }

}
