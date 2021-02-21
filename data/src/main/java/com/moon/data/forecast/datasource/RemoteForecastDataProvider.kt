package com.moon.data.forecast.datasource

import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastDomainModel
import io.reactivex.Single

class RemoteForecastDataProvider : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastDomainModel> {
        TODO("Not yet implemented")
    }

}
