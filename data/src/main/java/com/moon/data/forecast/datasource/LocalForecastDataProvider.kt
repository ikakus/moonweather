package com.moon.data.forecast.datasource

import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalForecastDataProvider(
    private val cache: SharedPrefsForecastCache
) : ForecastRepository {
    override fun get4DaysForecast(): Flow<ForecastListDomainModel> {
        return flow { emit(cache.get()!!) }
    }

    fun put(data: ForecastListDomainModel) {
        cache.put(data)
    }
}


