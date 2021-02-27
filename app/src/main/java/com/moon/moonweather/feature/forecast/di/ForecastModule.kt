package com.moon.moonweather.feature.forecast.di

import android.content.Context
import com.moon.data.ConnectivityProvider
import com.moon.data.ConnectivityProviderImpl
import com.moon.data.ForecastApi
import com.moon.data.forecast.datasource.*
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.di.Screen
import com.moon.moonweather.feature.forecast.ForecastBindings
import com.moon.moonweather.feature.forecast.ForecastFeature
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class ForecastModule {
    @Provides
    @Screen
    fun bindings(router: Router, feature: ForecastFeature): ForecastBindings {
        return ForecastBindings(router, feature)
    }

    @Provides
    @Screen
    fun provideForecastFeature(getForecastUseCase: GetForecastUseCase): ForecastFeature {
        return ForecastFeature(getForecastUseCase)
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
    fun provideMockedForecastProvider(): MockedForecastDataProvider {
        return MockedForecastDataProvider()
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