package com.moon.moonweather.feature.forecast

import com.moon.domain.forecast.model.ForecastDomainModel
import com.moon.domain.forecast.model.PlaceDomainModel
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.vmiflow.*
import kotlinx.coroutines.flow.*

class ForecastFeature(
    getForecastUseCase: GetForecastUseCase
) : ActorReducerFlowFeature<
        ForecastFeature.Wish,
        ForecastFeature.Effect,
        ForecastFeature.State,
        ForecastFeature.News
        >(
    initialState = State(loading = true),
    reducer = ReducerImpl(),
    actor = ActorImpl(getForecastUseCase),
    newsPublisher = NewsPublisherImpl(),
    bootstrapper = BootstrapperImpl()
) {

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                Effect.Loading -> state.copy(loading = true)
                is Effect.DataLoaded -> state.copy(forecastData = effect.data, loading = false)
                is Effect.Error -> state.copy(loading = false)
                is Effect.SelectDay -> state.copy(selectedDay = effect.day)
                else -> state
            }
        }
    }

    private class ActorImpl(
        private val getForecastUseCase: GetForecastUseCase
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Flow<Effect> = when (wish) {
            Wish.LoadData -> loadForecast()
            is Wish.SelectDay -> flow {
                val selection =  state.forecastData?.find { it.date == wish.day }
                selection?.let {
                    emit(Effect.SelectDay(selection))
                }
            }
            else -> flow { emit(Effect.NoEffect) }
        }

        private fun loadForecast(): Flow<Effect> {
            return getForecastUseCase()
                .onStart {
                    Effect.Loading
                }.catch { e ->
                    Effect.Error(e)
                }
                .map {
                    Effect.DataLoaded(it.forecasts)
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
        data class ShowPlaceDetails(val name: String) : Wish()
        data class SelectDay(val day: String): Wish()
    }

    sealed class Effect {
        object Loading : Effect()
        object NoEffect : Effect()
        data class Error(val throwable: Throwable) : Effect()
        data class DataLoaded(val data: List<ForecastDomainModel>) : Effect()
        data class SelectDay(val day: ForecastDomainModel): Effect()
    }

    data class State(
        val loading: Boolean = false,
        val forecastData: List<ForecastDomainModel>? = null,
        val selectedDay: ForecastDomainModel? = null
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