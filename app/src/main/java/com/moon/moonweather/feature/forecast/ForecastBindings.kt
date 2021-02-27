package com.moon.moonweather.feature.forecast

import android.content.Context
import android.widget.Toast
import com.badoo.mvicore.binder.using
import com.moon.moonweather.core.ResumePauseBindings
import com.moon.moonweather.navigation.MainFlowScreens
import io.reactivex.functions.Consumer
import ru.terrakok.cicerone.Router

class ForecastBindings(
    private val router: Router,
    private val feature: ForecastFeature,
) : ResumePauseBindings<ForecastFragment>() {
    override fun setup(view: ForecastFragment) {
        super.setup(view)
        binder?.bind(feature to view using UiModelTransformer(view.requireContext()))
        binder?.bind(view to feature using UiEventTransformer())
        binder?.bind(feature.news to NewsListener(view.context!!, router))
    }
}

class UiEventTransformer : (UiEvent) -> ForecastFeature.Wish? {
    override fun invoke(event: UiEvent): ForecastFeature.Wish? = when (event) {

        is UiEvent.PlaceClicked -> {
            ForecastFeature.Wish.ShowPlaceDetails(event.name)
        }
    }
}

class NewsListener(
    private val context: Context,
    private val router: Router
) : Consumer<ForecastFeature.News> {

    override fun accept(news: ForecastFeature.News) {
        when (news) {
            is ForecastFeature.News.LoactionWeatherDetails ->
                router.navigateTo(MainFlowScreens.PlaceDetailsScreen(news.name))

            is ForecastFeature.News.ErrorMessage ->
                errorHappened(news.throwable)
        }
    }

    private fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }
}

