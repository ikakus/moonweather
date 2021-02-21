package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel

interface PlaceDomainModel : DomainModel {
    val name: String
    val phenomenon: String
    val tempmax: Int?
    val tempmin: Int?
}
