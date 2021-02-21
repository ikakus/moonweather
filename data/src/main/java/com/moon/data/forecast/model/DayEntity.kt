package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.DayDomainModel
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.domain.forecast.model.WindDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayEntity(
    @Json(name = "peipsi")
    val peipsi: String?,
    @Json(name = "phenomenon")
    val phenomenon: String,
    @Json(name = "places")
    val places: List<PlaceDomainModel>?,
    @Json(name = "sea")
    val sea: String?,
    @Json(name = "tempmax")
    val tempmax: Int,
    @Json(name = "tempmin")
    val tempmin: Int,
    @Json(name = "text")
    val text: String,
    @Json(name = "winds")
    val winds: List<WindDomainModel>?
) : Entity {
    override fun mapToDomain(): DayDomainModel {
        TODO("Not yet implemented")
    }
}