package com.moon.data.forecast.datasource

import com.moon.data.ForecastApi
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

class RemoteForecastDataProvider(private val api: ForecastApi) : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastListDomainModel> {
        return api.get4DaysForecast().map {
            it as ForecastListDomainModel
        }
    }

}
