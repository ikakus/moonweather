package com.moon.moonweather.feature.forecast.utils

import com.moon.data.forecast.model.PlaceEntity
import com.moon.domain.forecast.model.ForecastDomainModel
import com.moon.moonweather.feature.forecast.ShortLocationUiModel

/**
 * Combine day and night data from response,
 * Day has only max and Night has only min values
 */
class PlacesZipper(private val forecastDomainModel: ForecastDomainModel) {
    fun getPlaces(): List<ShortLocationUiModel>? {
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
}