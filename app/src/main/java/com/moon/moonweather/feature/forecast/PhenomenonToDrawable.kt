package com.moon.moonweather.feature.forecast

import com.moon.moonweather.R

/**
 * Not sure what all the possible phenomenons can be
 * that's why mapping to things that came up in response
 */
class PhenomenonToDrawable() {
    fun dayToDrawable(phenomenon: String): Int {

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

    fun nightToDrawable(phenomenon: String): Int {

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