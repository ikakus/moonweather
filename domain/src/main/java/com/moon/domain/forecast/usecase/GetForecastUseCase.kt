package com.moon.domain.forecast.usecase

import com.moon.domain.base.NoArgUseCase
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastDomainModel
import io.reactivex.Single

class GetForecastUseCase(private val repository: ForecastRepository) :
    NoArgUseCase<ForecastDomainModel> {
    override fun invoke(): Single<ForecastDomainModel> {
        return repository.get4DaysForecast()
    }
}