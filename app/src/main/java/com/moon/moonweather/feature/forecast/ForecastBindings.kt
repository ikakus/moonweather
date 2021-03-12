package com.moon.moonweather.feature.forecast

import android.content.Context
import android.widget.Toast
import com.moon.moonweather.vmiflow.Bindings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

//class ForecastBindings(
//    private val router: Router,
//    private val feature: ForecastFeature,
//) : ResumePauseBindings<ForecastFragment>() {
//    override fun setup(view: ForecastFragment) {
//        super.setup(view)
////        binder?.bind(feature to view using UiModelTransformer(view.requireContext()))
////        binder?.bind(view to feature using UiEventTransformer())
////        binder?.bind(feature.news to NewsListener(view.context!!, router))
//    }
//}

@FlowPreview
@InternalCoroutinesApi
class TestBinder(
    private val feature: ForecastFeature,
) : Bindings<ForecastFragment>() {

    override fun setup(view: ForecastFragment, coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        bind(feature to view using UiModelTransformer(view.requireContext()))
        bind(view to feature using UiEventTransformer())
        bind(feature.news to NewsListener(view.context!!) using { it })
    }

//    private fun bind(connection: Pair<Flow<ForecastFeature.News>, NewsListener>) {
//        TODO("Not yet implemented")
//    }
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
) : FlowCollector<ForecastFeature.News> {

    override suspend fun emit(news: ForecastFeature.News) {
        when (news) {
            is ForecastFeature.News.PlaceWeatherDetails -> {
            }
//                router.navigateTo(MainFlowScreens.PlaceDetailsScreen(news.day, news.night))

            is ForecastFeature.News.ErrorMessage ->
                errorHappened(news.throwable)
        }
    }

    private fun errorHappened(throwable: Throwable) {
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }
}

