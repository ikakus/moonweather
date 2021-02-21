package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.WindDomainModel
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
) : Entity {
    override fun mapToDomain(): WindDomainModel {
        TODO("Not yet implemented")
    }
}