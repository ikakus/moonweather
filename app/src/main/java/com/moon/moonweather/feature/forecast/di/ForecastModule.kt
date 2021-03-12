package com.moon.moonweather.feature.forecast.di

import android.content.Context
import com.moon.data.ConnectivityProvider
import com.moon.data.ConnectivityProviderImpl
import com.moon.data.ForecastApi
import com.moon.data.forecast.datasource.*
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.SchedulerProvider
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.forecast.ForecastFeature
import com.moon.moonweather.feature.forecast.TestBinder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import ru.terrakok.cicerone.Router

@Module
class ForecastModule {
    @InternalCoroutinesApi
    @Provides
    @Screen
    fun bindings(router: Router, feature: ForecastFeature): TestBinder {
        return TestBinder(feature)
    }

    @Provides
    @Screen
    fun provideForecastFeature(
        schedulerProvider: SchedulerProvider,
        getForecastUseCase: GetForecastUseCase
    ): ForecastFeature {
        return ForecastFeature(schedulerProvider, getForecastUseCase)
    }

    @Provides
    fun provideForecastRepository(
        localForecastDataProvider: LocalForecastDataProvider,
        remoteForecastDataProvider: RemoteForecastDataProvider,
        connectivityProvider: ConnectivityProvider
    ): ForecastRepository {

        return ForecastRepositoryImpl(
            localForecastDataProvider = localForecastDataProvider,
            remoteForecastDataProvider = remoteForecastDataProvider,
            connectivityProvider = connectivityProvider
        )
    }

    @Provides
    fun provideConnectivityProvider(context: Context): ConnectivityProvider {
        return ConnectivityProviderImpl(context)
    }


    @Provides
    fun provideMockedForecastProvider(): StubForecastDataProvider {
        return StubForecastDataProvider()
    }

    @Provides
    fun provideLocalForecastProvider(
        sharedPrefsForecastCache: SharedPrefsForecastCache
    ): LocalForecastDataProvider {
        return LocalForecastDataProvider(sharedPrefsForecastCache)
    }

    @Provides
    fun provideSharedPrefsForecastCache(context: Context): SharedPrefsForecastCache {
        return SharedPrefsForecastCache(context)
    }

    @Provides
    fun provideRemoteForecastProvider(forecastApi: ForecastApi): RemoteForecastDataProvider {
        return RemoteForecastDataProvider(forecastApi)
    }

    @Provides
    fun provideForecastUsecase(repo: ForecastRepository): GetForecastUseCase {
        return GetForecastUseCase(repo)
    }
}