package com.moon.domain.forecast

import com.moon.domain.base.Repository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

interface ForecastRepository : Repository {
    fun get4DaysForecast(): Single<ForecastListDomainModel>
}