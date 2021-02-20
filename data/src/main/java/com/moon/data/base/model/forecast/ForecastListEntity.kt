package com.moon.data.base.model.forecast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastListEntity(
    @Json(name = "forecasts")
    val forecasts: List<ForecastEntity>
)