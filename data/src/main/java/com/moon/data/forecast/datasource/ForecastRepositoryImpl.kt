package com.moon.data.forecast.datasource

import com.moon.data.ConnectivityProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class ForecastRepositoryImpl(
    private val connectivityProvider: ConnectivityProvider,
    private val localForecastDataProvider: LocalForecastDataProvider,
    private val remoteForecastDataProvider: RemoteForecastDataProvider
) : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastListDomainModel> {
        return if (connectivityProvider.isInternetAvailable()) {
            remoteForecastDataProvider.get4DaysForecast().delay(5, TimeUnit.SECONDS)
        } else {
            localForecastDataProvider.get4DaysForecast()
        }
    }
}
