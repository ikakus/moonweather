package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.PlaceDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceEntity(
    @Json(name = "name")
    val name: String,
    @Json(name = "phenomenon")
    val phenomenon: String,
    @Json(name = "tempmax")
    val tempmax: Int?,
    @Json(name = "tempmin")
    val tempmin: Int?
) : Entity {
    override fun mapToDomain(): PlaceDomainModel {
        TODO("Not yet implemented")
    }
}