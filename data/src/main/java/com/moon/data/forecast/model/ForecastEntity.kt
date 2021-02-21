package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.DayDomainModel
import com.moon.domain.forecast.model.ForecastDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastEntity(
    @Json(name = "date")
    val date: String,
    @Json(name = "day")
    val day: DayDomainModel,
    @Json(name = "night")
    val night: DayDomainModel
) : Entity {
    override fun mapToDomain(): ForecastDomainModel {
        TODO("Not yet implemented")
    }
}