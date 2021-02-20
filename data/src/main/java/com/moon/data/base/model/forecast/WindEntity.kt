package com.moon.data.base.model.forecast
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindEntity(
    @Json(name = "direction")
    val direction: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "speedmax")
    val speedmax: Int,
    @Json(name = "speedmin")
    val speedmin: Int
)