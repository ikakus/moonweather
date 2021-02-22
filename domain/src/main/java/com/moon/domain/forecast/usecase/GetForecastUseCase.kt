package com.moon.domain.forecast.usecase

import com.moon.domain.base.NoArgUseCase
import com.moon.domain.forecast.ForecastRepository
import com.moon.domain.forecast.model.ForecastListDomainModel
import io.reactivex.Single

class GetForecastUseCase(private val repository: ForecastRepository) :
    NoArgUseCase<ForecastListDomainModel> {
    override fun invoke(): Single<ForecastListDomainModel> {
        return repository.get4DaysForecast()
    }
}