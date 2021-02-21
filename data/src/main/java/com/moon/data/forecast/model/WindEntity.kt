package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.WindDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindEntity(
    @Json(name = "direction")
    override val direction: String,
    @Json(name = "name")
    override val name: String,
    @Json(name = "speedmax")
    override val speedmax: Int,
    @Json(name = "speedmin")
    override val speedmin: Int
) : Entity, WindDomainModel