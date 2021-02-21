package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel

interface WindDomainModel : DomainModel {
    val direction: String
    val name: String
    val speedmax: Int
    val speedmin: Int
}
