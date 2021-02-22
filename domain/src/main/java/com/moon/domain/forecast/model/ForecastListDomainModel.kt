package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel

interface ForecastListDomainModel : DomainModel {
    val forecasts: List<ForecastDomainModel>
}
