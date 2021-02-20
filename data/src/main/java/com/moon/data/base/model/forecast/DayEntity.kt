package com.moon.data.base.model.forecast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayEntity(
    @Json(name = "peipsi")
    val peipsi: String,
    @Json(name = "phenomenon")
    val phenomenon: String,
    @Json(name = "places")
    val places: List<PlaceEntity>,
    @Json(name = "sea")
    val sea: String,
    @Json(name = "tempmax")
    val tempmax: Int,
    @Json(name = "tempmin")
    val tempmin: Int,
    @Json(name = "text")
    val text: String,
    @Json(name = "winds")
    val winds: List<WindEntity>
)