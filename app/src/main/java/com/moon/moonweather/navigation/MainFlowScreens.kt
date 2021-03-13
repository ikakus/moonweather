package com.moon.moonweather.navigation

import androidx.fragment.app.Fragment
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.moonweather.feature.forecast.ForecastFragment
import com.moon.moonweather.feature.placedetails.PlaceDetailsFragment
import kotlinx.coroutines.InternalCoroutinesApi
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowScreens {

    class ForecastScreen : SupportAppScreen() {
        @InternalCoroutinesApi
        override fun getFragment(): Fragment {
            return ForecastFragment()
        }
    }

    class PlaceDetailsScreen(val day: PlaceDomainModel?, val night: PlaceDomainModel?) :
        SupportAppScreen() {
        override fun getFragment(): Fragment {
            return PlaceDetailsFragment.newInstance(day, night)
        }
    }
}