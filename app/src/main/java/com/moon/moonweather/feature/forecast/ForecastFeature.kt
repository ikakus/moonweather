package com.moon.moonweather.feature.forecast

import com.moon.domain.forecast.model.ForecastDomainModel
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.SchedulerProvider
import com.moon.moonweather.vmiflow.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ForecastFeature(
    schedulerProvider: SchedulerProvider,
    getForecastUseCase: GetForecastUseCase
) : ActorReducerFlowFeature<
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
        private val getForecastUseCase: GetForecastUseCase
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Flow<Effect> = when (wish) {
            Wish.LoadData -> loadForecast()
            else -> flow { emit(Effect.NoEffect) }
        }

        private fun loadForecast(): Flow<Effect> {
//            return getForecastUseCase()
//                .subscribeOn(schedulerProvider.io())
//                .observeOn(schedulerProvider.ui())
//                .toObservable()
//                .map {
//                    Effect.DataLoaded(it.forecasts) as Effect
//                }
//                .startWith(Effect.Loading)
//                .onErrorReturn { Effect.Error(it) }
            return flow { emit(Effect.NoEffect) }
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
        override fun invoke(): Flow<Wish> {
            return flow { emit(Wish.LoadData) }
        }
    }
}