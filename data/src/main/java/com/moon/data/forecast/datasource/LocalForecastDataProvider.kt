package com.moon.data.forecast.datasource

import com.moon.data.forecast.model.DayEntity
import com.moon.data.forecast.model.ForecastEntity
import com.moon.data.forecast.model.ForecastListEntity
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

class LocalForecastDataProvider : ForecastRepository {
    override fun get4DaysForecast(): Single<ForecastListDomainModel> {
        return Single.just(getMockedData())
    }

}

fun getMockedData(): ForecastListDomainModel {
    val forecast = ForecastEntity(
        date = "test Date",
        day = DayEntity(
            peipsi = null,
            phenomenon = "phen",
            places = null,
            sea = null,
            tempmax = 20,
            tempmin = 1,
            text = "Sample text",
            winds = null
        ),
        night = DayEntity(
            peipsi = null,
            phenomenon = "phen",
            places = null,
            sea = null,
            tempmax = 20,
            tempmin = 1,
            text = "Sample text",
            winds = null
        )
    )
    return ForecastListEntity(forecasts = listOf(forecast)) as ForecastListDomainModel
}