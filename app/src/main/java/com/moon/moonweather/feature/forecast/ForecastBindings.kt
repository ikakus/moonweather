package com.moon.moonweather.feature.forecast

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.moon.moonweather.navigation.MainFlowScreens
import com.moon.moonweather.vmiflow.Binder
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import ru.terrakok.cicerone.Router

@FlowPreview
@InternalCoroutinesApi
class ForecastBindings(
    private val router: Router,
    private val feature: ForecastFeature,
) : Binder<ForecastFragment>(), LifecycleObserver {

    override fun setup(view: ForecastFragment) {
        this.coroutineScope = view.lifecycleScope
        view.lifecycle.addObserver(this)
        bind(feature to view using UiModelTransformer(view.requireContext()))
        bind(view to feature using UiEventTransformer())
        bind(feature.news to NewsListener(view.requireContext(), router) using { it })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun disposeOnPause() {
        dispose()
    }

}

class UiEventTransformer : (UiEvent) -> ForecastFeature.Wish? {
    override fun invoke(event: UiEvent): ForecastFeature.Wish? {
        return when (event) {


            is UiEvent.PlaceClicked -> {
                ForecastFeature.Wish.ShowPlaceDetails(event.name)
            }
            is UiEvent.PagerDaySelected -> {
                ForecastFeature.Wish.SelectDay(event.pageDay.id)
            }
        }
    }
}

class NewsListener(
    private val context: Context,
    private val router: Router,
) : FlowCollector<ForecastFeature.News> {

    override suspend fun emit(news: ForecastFeature.News) {
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

