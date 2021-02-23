package com.moon.moonweather.feature.forecast

import com.moon.data.ConnectivityProvider
import com.moon.data.ConnectivityProviderImpl
import com.moon.data.ForecastApi
import com.moon.data.forecast.datasource.ForecastRepositoryImpl
import com.moon.data.forecast.datasource.LocalForecastDataProvider
import com.moon.data.forecast.datasource.RemoteForecastDataProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.di.Screen
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
    fun provideConnectivityProvider(): ConnectivityProvider {
        return ConnectivityProviderImpl()
    }


    @Provides
    fun provideLocalForecastProvider(): LocalForecastDataProvider {
        return LocalForecastDataProvider()
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