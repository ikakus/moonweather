package com.moon.moonweather.feature.forecast

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.moon.moonweather.navigation.MainFlowScreens
import com.moon.moonweather.vmiflow.Binder
import com.moon.moonweather.vmiflow.tag
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import ru.terrakok.cicerone.Router

@FlowPreview
@InternalCoroutinesApi
class ForecastBindings(
    private val router: Router,
    private val feature: ForecastFeature,
) : Binder<ForecastFragment>() {

    override fun setup(view: ForecastFragment) {
        this.coroutineScope = view.lifecycleScope
        this.feature.coroutineScope = view.lifecycleScope
        bind(feature to view using UiModelTransformer(view.requireContext()))
        bind(view to feature using UiEventTransformer())
        bind(feature.news to NewsListener(view.context!!, router) using { it })
    }

    fun dispose() {
        cancel()
        feature.cancel()
    }

}

class UiEventTransformer : (UiEvent) -> ForecastFeature.Wish? {
    override fun invoke(event: UiEvent): ForecastFeature.Wish? {
        Log.d(tag, this.toString())
        return when (event) {


            is UiEvent.PlaceClicked -> {
                ForecastFeature.Wish.ShowPlaceDetails(event.name)
            }
        }
    }
}

class NewsListener(
    private val context: Context,
    private val router: Router,
) : FlowCollector<ForecastFeature.News> {

    override suspend fun emit(news: ForecastFeature.News) {
        Log.d(tag, this.toString() + news)
        when (news) {
            is ForecastFeature.News.PlaceWeatherDetails ->
                router.navigateTo(MainFlowScreens.PlaceDetailsScreen(news.day, news.night))

            is ForecastFeature.News.ErrorMessage ->
                errorHappened(news.throwable)
        }
    }

    private fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }
}

