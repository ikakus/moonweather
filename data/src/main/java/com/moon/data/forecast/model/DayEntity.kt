package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.DayDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayEntity(
    @Json(name = "peipsi")
    override val peipsi: String?,
    @Json(name = "phenomenon")
    override val phenomenon: String,
    @Json(name = "places")
    override val places: List<PlaceEntity>?,
    @Json(name = "sea")
    override val sea: String?,
    @Json(name = "tempmax")
    override val tempmax: Int,
    @Json(name = "tempmin")
    override val tempmin: Int,
    @Json(name = "text")
    override val text: String,
    @Json(name = "winds")
    override val winds: List<WindEntity>?
) : Entity, DayDomainModel