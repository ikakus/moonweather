package com.moon.moonweather.feature.forecast

import com.moon.data.forecast.datasource.LocalForecastDataProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.usecase.GetForecastUseCase
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class ForecastModule {
    @Provides
    fun bindings(router: Router, feature: ForecastFeature): ForecastBindings {
        return ForecastBindings(router, feature)
    }

    @Provides
    fun provideForecastFeature(getForecastUseCase: GetForecastUseCase): ForecastFeature {
        return ForecastFeature(getForecastUseCase)
    }

    @Provides
    fun provideForecastRepository(): ForecastRepository {
        return LocalForecastDataProvider()
    }

    @Provides
    fun provideForecastUsecase(repo: ForecastRepository): GetForecastUseCase {
        return GetForecastUseCase(repo)
    }
}