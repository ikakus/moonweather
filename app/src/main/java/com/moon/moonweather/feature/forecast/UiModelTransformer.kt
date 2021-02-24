package com.moon.moonweather.feature.forecast

import android.content.Context
import com.moon.moonweather.R

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
                        peipsi = it.day.peipsi.orEmpty(),
                        drawableResource = phenomenonDayToDrawable(it.day.phenomenon)
                    ),
                    night = ForecastUiModel(
                        text = it.night.text,
                        phenomenon = it.night.phenomenon,
                        min = it.night.tempmin.toString(),
                        max = it.night.tempmax.toString(),
                        peipsi = it.night.peipsi.orEmpty(),
                        drawableResource = phenomenonNightToDrawable(it.night.phenomenon)
                    ),
                    places = it.day.places?.map { place ->
                        MinorLocation(place.name)
                    }
                )
            }
        )
    }

    private fun phenomenonDayToDrawable(phenomenon: String): Int {

        if (phenomenon.contains("shower", true) ||
            phenomenon.contains("rain", true)
        ) {
            return R.drawable.day_rain
        }

        if (phenomenon.contains("snow", true)) {
            return R.drawable.day_snow
        }

        return R.drawable.day_clear
    }

    private fun phenomenonNightToDrawable(phenomenon: String): Int {

        if (phenomenon.contains("shower", true) ||
            phenomenon.contains("rain", true)
        ) {
            return R.drawable.night_half_moon_rain
        }


        if (phenomenon.contains("snow", true)) {
            return R.drawable.night_half_moon_snow
        }

        return R.drawable.night_half_moon_clear
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
    val drawableResource: Int,
    val min: String,
    val max: String,
    val phenomenon: String,
    val peipsi: String,
    val text: String
)
