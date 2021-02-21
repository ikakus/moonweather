package com.moon.moonweather.feature.forecast

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.moon.domain.forecast.usecase.GetForecastUseCase
import io.reactivex.Observable

class ForecastFeature(
    getForecastUseCase: GetForecastUseCase
) :
    ActorReducerFeature<ForecastFeature.Wish, ForecastFeature.Effect, ForecastFeature.State, ForecastFeature.News>(
        initialState = State(loading = false),
        reducer = ReducerImpl(),
        actor = ActorImpl(getForecastUseCase),
        newsPublisher = NewsPublisherImpl()
    ) {

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                Effect.Loading -> state

            }
        }
    }

    private class ActorImpl(private val loginUserUseCase: GetForecastUseCase) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            Wish.DayDetails -> details(state)
        }

        private fun details(state: State): Observable<Effect> {
            TODO("Not yet implemented")
        }
    }

    private class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? {
            when (wish) {
                is Wish.DayDetails -> return News.Details
            }
            when (effect) {
//                is Effect.Loading -> return News.ErrorOccurred(Throwable("Logging In error"))
            }
            return null
        }
    }

    sealed class Wish {
        object DayDetails : Wish()
    }

    sealed class Effect {
        object Loading : Effect()
    }

    data class State(
        val loading: Boolean = false,
    )

    sealed class News {
        object Details : News()
    }
}