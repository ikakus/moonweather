package com.moon.data.forecast.datasource

import com.moon.data.ConnectivityProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

class ForecastRepositoryImpl(
    private val connectivityProvider: ConnectivityProvider,
    private val localForecastDataProvider: LocalForecastDataProvider,
    private val remoteForecastDataProvider: RemoteForecastDataProvider
) : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastListDomainModel> {
        return if (connectivityProvider.isInternetAvailable()) {
            remoteForecastDataProvider.get4DaysForecast()
                .map { model ->
                    localForecastDataProvider.put(model)
                    model
                }
        } else {
            localForecastDataProvider.get4DaysForecast()
        }
    }
}
