package com.moon.data.base

import com.moon.domain.base.DomainModel

interface Entity {
    fun mapToDomain(): DomainModel
}

