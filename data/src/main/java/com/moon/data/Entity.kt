package com.moon.data

import com.moon.domain.base.DomainModel

interface Entity {
    fun mapToDomain(): DomainModel
}

