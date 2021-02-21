package com.moon.moonweather.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.moon.moonweather.R
import com.moon.moonweather.componentprovider.main.MainComponentProvider
import com.moon.moonweather.navigation.MainFlowScreens
import com.moon.moonweather.views.GlobalLoading
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), GlobalLoading {

    private lateinit var navigator: Navigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainComponentProvider.get().inject(this)

        navigator = SupportAppNavigator(this, R.id.main_container)
        router.newRootScreen(MainFlowScreens.ForecastScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun loading(show: Boolean) {
        if (show) {
            loading_verlay.visibility = View.VISIBLE
        } else {
            loading_verlay.visibility = View.GONE
        }
    }
}