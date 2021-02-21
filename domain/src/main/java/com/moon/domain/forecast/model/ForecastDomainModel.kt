package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel

interface ForecastDomainModel : DomainModel {
    val date: String
    val day: DayDomainModel
    val night: DayDomainModel
}
