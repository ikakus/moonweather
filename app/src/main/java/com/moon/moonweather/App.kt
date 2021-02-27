package com.moon.moonweather

import android.app.Application
import com.moon.moonweather.componentprovider.app.AppComponentProvider
import java.lang.ref.WeakReference

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponentProvider.context = WeakReference(this)
        AppComponentProvider.get().inject(this)
    }
}
