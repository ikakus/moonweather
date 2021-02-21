package com.moon.moonweather.componentprovider.app

import android.annotation.SuppressLint
import android.content.Context
import com.moon.moonweather.core.di.DaggerComponentProvider

@SuppressLint("StaticFieldLeak")
object AppComponentProvider : DaggerComponentProvider<AppComponent>() {

    lateinit var context: Context

    override fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .provideContext(context)
            .build()
    }
}
