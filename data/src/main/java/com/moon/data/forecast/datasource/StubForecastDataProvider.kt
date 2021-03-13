package com.moon.data.forecast.datasource

import com.moon.data.forecast.model.DayEntity
import com.moon.data.forecast.model.ForecastEntity
import com.moon.data.forecast.model.ForecastListEntity
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StubForecastDataProvider : ForecastRepository {
    override fun get4DaysForecast(): Flow<ForecastListDomainModel> {
        return flow { emit(getMockedData()) }
    }
}

fun getMockedData(): ForecastListDomainModel {
    val forecast = ForecastEntity(
        date = "2021-02-24",
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
    return ForecastListEntity(forecasts = listOf(forecast))
}