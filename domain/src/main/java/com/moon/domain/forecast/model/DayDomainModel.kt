package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel

interface DayDomainModel : DomainModel {
    val peipsi: String?
    val phenomenon: String
    val places: List<PlaceDomainModel>?
    val sea: String?
    val tempmax: Int
    val tempmin: Int
    val text: String
    val winds: List<WindDomainModel>?
}
