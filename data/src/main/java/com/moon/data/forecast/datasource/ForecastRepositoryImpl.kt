package com.moon.data.forecast.datasource

import com.moon.data.ConnectivityProvider
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ForecastRepositoryImpl(
    private val connectivityProvider: ConnectivityProvider,
    private val localForecastDataProvider: LocalForecastDataProvider,
    private val remoteForecastDataProvider: RemoteForecastDataProvider
) : ForecastRepository {
    override fun get4DaysForecast(): Flow<ForecastListDomainModel> {
        return if (connectivityProvider.isInternetAvailable()) {
            flow {
                remoteForecastDataProvider.get4DaysForecast()
                    .map { model ->
                        localForecastDataProvider.put(model)
                        model
                    }
            }

        } else {
            flow {
                localForecastDataProvider.get4DaysForecast()
            }
        }
    }
}
