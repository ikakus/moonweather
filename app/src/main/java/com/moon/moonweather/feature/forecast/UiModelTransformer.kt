package com.moon.moonweather.feature.forecast

import android.content.Context

class UiModelTransformer(val context: Context) : (ForecastFeature.State) -> UiModel {
    override fun invoke(state: ForecastFeature.State): UiModel {
        return UiModel(
            loading = state.loading,
            forecastDays = state.forecastData?.map {
                ForecastDayUiModel(
                    day = ForecastUiModel(
                        text = it.day.text,
                        phenomenon = it.day.phenomenon,
                        min = it.day.tempmin.toString(),
                        max = it.day.tempmax.toString(),
                        peipsi = it.day.peipsi.orEmpty()
                    ),
                    night = ForecastUiModel(
                        text = it.night.text,
                        phenomenon = it.night.phenomenon,
                        min = it.day.tempmin.toString(),
                        max = it.day.tempmax.toString(),
                        peipsi = it.day.peipsi.orEmpty()
                    )
                )
            }
        )
    }
}

sealed class UiEvent {
    data class DayClicked(val text: String) : UiEvent()
}

data class UiModel(
    val loading: Boolean,
    val forecastDays: List<ForecastDayUiModel>?
)

data class ForecastDayUiModel(
    val day: ForecastUiModel,
    val night: ForecastUiModel,
    val places: List<MinorLocation>? = null
)

data class MinorLocation(
    val name: String
)

data class ForecastUiModel(
    val min: String,
    val max: String,
    val phenomenon: String,
    val peipsi: String,
    val text: String
)
