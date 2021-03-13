package com.moon.domain.forecast

import com.moon.domain.base.Repository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow

interface ForecastRepository : Repository {
    fun get4DaysForecast(): Flow<ForecastListDomainModel>
}