package com.moon.moonweather.feature.forecast

import android.content.Context
import com.moon.data.forecast.model.PlaceEntity
import com.moon.domain.forecast.model.ForecastDomainModel
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
                    places = getPlaces(it)
                )
            }
        )
    }

    private fun getPlaces(forecastDomainModel: ForecastDomainModel): List<ShortLocationUiModel>? {
        val locations = mutableListOf<ShortLocationUiModel>()
        val dayPlaces = forecastDomainModel.day.places
        val nightPlaces = forecastDomainModel.night.places

        val combined = dayPlaces?.zip(nightPlaces!!) { a, b ->
            PlaceEntity(
                name = a.name,
                phenomenon = a.phenomenon,
                tempmax = a.tempmax ?: b.tempmax,
                tempmin = a.tempmin ?: b.tempmin
            )
        }

        combined?.forEach { place ->
            locations.add(
                ShortLocationUiModel(
                    name = place.name,
                    max = place.tempmax.toString(),
                    min = place.tempmin.toString()
                )
            )

        }

        return if (locations.isEmpty()) {
            null
        } else {
            locations
        }
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
    val places: List<ShortLocationUiModel>? = null
)

data class ShortLocationUiModel(
    val name: String,
    val min: String,
    val max: String
)

data class ForecastUiModel(
    val drawableResource: Int,
    val min: String,
    val max: String,
    val phenomenon: String,
    val peipsi: String,
    val text: String
)
