package com.moon.data.forecast.model

import com.moon.domain.forecast.model.ForecastDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastListEntity(
    @Json(name = "forecasts")
    val forecasts: List<ForecastDomainModel>
)