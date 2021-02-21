package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.PlaceDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceEntity(
    @Json(name = "name")
    override val name: String,
    @Json(name = "phenomenon")
    override val phenomenon: String,
    @Json(name = "tempmax")
    override val tempmax: Int?,
    @Json(name = "tempmin")
    override val tempmin: Int?
) : Entity, PlaceDomainModel