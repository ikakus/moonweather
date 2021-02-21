package com.moon.moonweather.navigation

import androidx.fragment.app.Fragment
import com.moon.moonweather.feature.home.HomeFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowScreens {
    class HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HomeFragment()
        }
    }
}