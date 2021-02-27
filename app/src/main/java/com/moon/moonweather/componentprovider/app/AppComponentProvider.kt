package com.moon.moonweather.componentprovider.app

import android.annotation.SuppressLint
import android.content.Context
import com.moon.moonweather.core.di.DaggerComponentProvider
import java.lang.ref.WeakReference

@SuppressLint("StaticFieldLeak")
object AppComponentProvider : DaggerComponentProvider<AppComponent>() {

    lateinit var context: WeakReference<Context>

    override fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .provideContext(requireNotNull(context.get()))
            .build()
    }
}
