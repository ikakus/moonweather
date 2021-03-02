package com.moon.moonweather.common.di

import com.moon.moonweather.core.SchedulerProvideImpl
import com.moon.moonweather.core.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class RxUtils {
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProvideImpl()
    }
}