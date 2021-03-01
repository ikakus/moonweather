package com.moon.moonweather.feature.forecast

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.moon.domain.forecast.model.ForecastDomainModel
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.SchedulerProvider
import io.reactivex.Observable

class ForecastFeature(
    schedulerProvider: SchedulerProvider,
    getForecastUseCase: GetForecastUseCase
) : ActorReducerFeature<
        ForecastFeature.Wish,
        ForecastFeature.Effect,
        ForecastFeature.State,
        ForecastFeature.News
        >(
    initialState = State(loading = false),
    reducer = ReducerImpl(),
    actor = ActorImpl(schedulerProvider, getForecastUseCase),
    newsPublisher = NewsPublisherImpl(),
    bootstrapper = BootstrapperImpl()
) {

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                Effect.Loading -> state.copy(loading = true)
                is Effect.DataLoaded -> state.copy(forecastData = effect.data, loading = false)
                is Effect.Error -> state.copy(loading = false)
                else -> state
            }
        }
    }

    private class ActorImpl(
        private val schedulerProvider: SchedulerProvider,
        private val getForecastUseCase: GetForecastUseCase?
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            Wish.LoadData -> loadForecast()
            Wish.Refresh -> loadForecast()
            else -> Observable.just(Effect.NoEffect)
        }

        private fun loadForecast(): Observable<Effect> {
            val useCase = getForecastUseCase?.invoke()
            return if (useCase == null) {
                Observable.just(Effect.NoEffect)
            } else {
                useCase
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .toObservable()
                    .map {
                        Effect.DataLoaded(it.forecasts) as Effect
                    }
                    .startWith(Effect.Loading)
                    .onErrorReturn { Effect.Error(it) }
            }

        }
    }

    private class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? {
            when (wish) {
                is Wish.ShowPlaceDetails -> {
                    state.forecastData?.get(0)?.let { forecast ->
                        val day = forecast.day.places?.find { it.name == wish.name }
                        val night = forecast.night.places?.find { it.name == wish.name }
                        return News.PlaceWeatherDetails(day, night)
                    }
                }
            }
            when (effect) {
                is Effect.Error -> return News.ErrorMessage(effect.throwable)
            }
            return null
        }
    }

    sealed class Wish {
        object LoadData : Wish()
        object Refresh : Wish()
        data class ShowPlaceDetails(val name: String) : Wish()
    }

    sealed class Effect {
        object Loading : Effect()
        object NoEffect : Effect()
        data class Error(val throwable: Throwable) : Effect()
        data class DataLoaded(val data: List<ForecastDomainModel>) : Effect()
    }

    data class State(
        val loading: Boolean = false,
        val forecastData: List<ForecastDomainModel>? = null
    )

    sealed class News {
        data class PlaceWeatherDetails(
            val day: PlaceDomainModel?,
            val night: PlaceDomainModel?
        ) : News()

        data class ErrorMessage(val throwable: Throwable) : News()
    }

    private class BootstrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> {
            return Observable.just(Wish.LoadData)
        }
    }
}