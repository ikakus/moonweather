package com.moon.domain.forecast.usecase

import com.moon.domain.base.NoArgUseCase
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import kotlinx.coroutines.flow.Flow

class GetForecastUseCase(private val repository: ForecastRepository) :
    NoArgUseCase<ForecastListDomainModel> {
    override fun invoke(): Flow<ForecastListDomainModel> {
        return repository.get4DaysForecast()
    }
}