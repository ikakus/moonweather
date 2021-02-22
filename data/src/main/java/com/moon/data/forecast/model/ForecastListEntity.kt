package com.moon.data.forecast.model

import com.moon.data.base.Entity
import com.moon.domain.forecast.model.ForecastListDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastListEntity(
    @Json(name = "forecasts")
    override val forecasts: List<ForecastEntity>
) : Entity, ForecastListDomainModel