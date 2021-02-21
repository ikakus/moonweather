package com.moon.domain.forecast

import com.moon.domain.forecast.model.ForecastDomainModel
import io.reactivex.Single

interface ForecastRepository {
    fun get4DaysForecast(): Single<ForecastDomainModel>
}