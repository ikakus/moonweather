package com.moon.data.forecast.datasource

import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

class LocalForecastDataProvider(
    private val cache: SharedPrefsForecastCache
) : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastListDomainModel> {
        return Single.just(cache.get())
    }

    fun put(data: ForecastListDomainModel) {
        cache.put(data)
    }
}


