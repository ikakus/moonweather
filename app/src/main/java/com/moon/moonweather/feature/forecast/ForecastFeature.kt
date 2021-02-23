package com.moon.moonweather.feature.forecast

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.moon.domain.forecast.usecase.GetForecastUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastFeature(
    getForecastUseCase: GetForecastUseCase
) :
    ActorReducerFeature<ForecastFeature.Wish, ForecastFeature.Effect, ForecastFeature.State, ForecastFeature.News>(
        initialState = State(loading = false),
        reducer = ReducerImpl(),
        actor = ActorImpl(getForecastUseCase),
        newsPublisher = NewsPublisherImpl(),
        bootstrapper = BootstrapperImpl()
    ) {

    private class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                Effect.Loading -> state.copy(loading = true)
                is Effect.DataLoaded -> state.copy(text = effect.data, loading = false)
                Effect.Error -> TODO()
            }
        }
    }

    private class ActorImpl(private val getForecastUseCase: GetForecastUseCase) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            Wish.DayDetails -> details(state)
            Wish.LoadData -> details(state)
        }

        private fun details(state: State): Observable<Effect> {
            return getForecastUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .map {
                    Effect.DataLoaded(it.toString()) as Effect
                }
                .startWith(Effect.Loading)
                .onErrorReturn { Effect.Error }
        }
    }

    private class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? {
            return when (wish) {
                is Wish.DayDetails -> News.Details
                else -> null
            }
        }
    }

    sealed class Wish {
        object LoadData : Wish()
        object DayDetails : Wish()
    }

    sealed class Effect {
        object Loading : Effect()
        object Error : Effect()
        data class DataLoaded(val data: String) : Effect()
    }

    data class State(
        val loading: Boolean = false,
        val text: String = "initial"
    )

    sealed class News {
        object Details : News()
    }

    private class BootstrapperImpl : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> {
            return Observable.just(Wish.LoadData)
        }
    }
}