package com.moon.data

import com.moon.data.forecast.model.ForecastListEntity
import retrofit2.http.GET

interface ForecastApi {
    @GET("forecast")
    fun get4DaysForecast(): ForecastListEntity
}