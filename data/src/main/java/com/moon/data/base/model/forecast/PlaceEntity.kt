package com.moon.data.base.model.forecast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceEntity(
    @Json(name = "name")
    val name: String,
    @Json(name = "phenomenon")
    val phenomenon: String,
    @Json(name = "tempmax")
    val tempmax: Int,
    @Json(name = "tempmin")
    val tempmin: Int
)