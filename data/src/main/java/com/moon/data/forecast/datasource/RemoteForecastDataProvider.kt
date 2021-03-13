package com.moon.data.forecast.datasource

import com.moon.data.ForecastApi
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteForecastDataProvider(private val api: ForecastApi) : ForecastRepository {
    override fun get4DaysForecast(): Flow<ForecastListDomainModel> {
        return flow {
            emit(api.get4DaysForecast())
        }
    }
}
