package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.ForecastDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastListEntity(
    @Json(name = "forecasts")
    val forecasts: List<ForecastDomainModel>
) : Entity