package com.moon.moonweather.vmiflow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestFeature : BaseFlowFeature<
        TestFeature.Wish,
        TestFeature.Action,
        TestFeature.Effect,
        TestFeature.State,
        TestFeature.News
        >(
    initialState = State(loading = false),
    reducer = ReducerImpl(),
    actor = ActorImpl(),
    newsPublisher = NewsPublisherImpl(),
    wishToAction = { wish -> Action.SomeAction }
) {

    data class State(
        val loading: Boolean
    )

    sealed class Wish {
        object Load : Wish()
    }

    sealed class Effect {
        object Loading : Effect()
        object NoEffect : Effect()
    }

    sealed class News {
        object SomeNews : News()
    }

    sealed class Action {
        object SomeAction : Action()
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            return when (effect) {
                Effect.Loading -> {
                    state.copy(loading = state.loading.not())
                }
                Effect.NoEffect -> state.copy(loading = false)
            }
        }

    }

    class ActorImpl : Actor<State, Action, Effect> {
        override fun invoke(state: State, action: Action): Flow<Effect> {
            return when (action) {
                Action.SomeAction -> flow { emit(Effect.Loading) }
            }
        }

    }

    private class NewsPublisherImpl : NewsPublisher<Action, Effect, State, News> {
        override fun invoke(action: Action, effect: Effect, state: State): News? {
            return null
        }

    }
}

