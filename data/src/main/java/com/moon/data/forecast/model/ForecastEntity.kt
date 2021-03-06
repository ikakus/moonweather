package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.ForecastDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastEntity(
    @Json(name = "date")
    override val date: String,
    @Json(name = "day")
    override val day: DayEntity,
    @Json(name = "night")
    override val night: DayEntity
) : Entity, ForecastDomainModel