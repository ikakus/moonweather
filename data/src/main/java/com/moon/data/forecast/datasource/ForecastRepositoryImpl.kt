package com.moon.data.forecast.datasource

import com.moon.data.ConnectivityProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastDomainModel
import io.reactivex.Single

class ForecastRepositoryImpl(
    private val connectivityProvider: ConnectivityProvider,
    private val localForecastDataProvider: LocalForecastDataProvider,
    private val remoteForecastDataProvider: RemoteForecastDataProvider
) : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastDomainModel> {
        return if (connectivityProvider.isInternetAvailable()) {
            remoteForecastDataProvider.get4DaysForecast()
        } else {
            localForecastDataProvider.get4DaysForecast()
        }
    }
}