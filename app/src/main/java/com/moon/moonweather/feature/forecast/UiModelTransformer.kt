package com.moon.moonweather.feature.forecast

import android.content.Context
import com.moon.domain.forecast.model.ForecastDomainModel

class UiModelTransformer(val context: Context) : (ForecastFeature.State) -> UiModel {
    override fun invoke(state: ForecastFeature.State): UiModel {
        return UiModel(
            loading = state.loading,
            forecastDays = state.forecastData?.map { it.mapToUi() }
        )
    }
}

private fun ForecastDomainModel.mapToUi(): ForecastDayUiModel {
    val placesZipper = PlacesZipper(this)
    val phMapper = PhenomenonToDrawable()
    return ForecastDayUiModel(
        date = date,
        day = ForecastUiModel(
            text = day.text,
            phenomenon = day.phenomenon,
            min = day.tempmin.toString(),
            max = day.tempmax.toString(),
            peipsi = day.peipsi.orEmpty(),
            drawableResource = phMapper.dayToDrawable(day.phenomenon)
        ),
        night = ForecastUiModel(
            text = night.text,
            phenomenon = night.phenomenon,
            min = night.tempmin.toString(),
            max = night.tempmax.toString(),
            peipsi = night.peipsi.orEmpty(),
            drawableResource = phMapper.nightToDrawable(night.phenomenon)
        ),
        places = placesZipper.getPlaces()
    )
}

sealed class UiEvent {
    data class DayClicked(val text: String) : UiEvent()
}

data class UiModel(
    val loading: Boolean,
    val forecastDays: List<ForecastDayUiModel>?
)

data class ForecastDayUiModel(
    val date: String,
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
