package com.moon.moonweather.navigation

import androidx.fragment.app.Fragment
import com.moon.moonweather.feature.forecast.ForecastFragment
import com.moon.moonweather.feature.locationdetails.LocationDetailsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowScreens {

    class ForecastScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ForecastFragment()
        }
    }

    class LocationDetailsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LocationDetailsFragment()
        }
    }
}