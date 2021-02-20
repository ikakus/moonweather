package com.moon.data.base.model.forecast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastEntity(
    @Json(name = "date")
    val date: String,
    @Json(name = "day")
    val day: DayEntity,
    @Json(name = "night")
    val night: DayEntity
)