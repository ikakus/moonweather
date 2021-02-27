package com.moon.domain.forecast.model

import com.moon.domain.base.DomainModel
import java.io.Serializable

interface PlaceDomainModel : DomainModel, Serializable {
    val name: String
    val phenomenon: String
    val tempmax: Int?
    val tempmin: Int?
}
