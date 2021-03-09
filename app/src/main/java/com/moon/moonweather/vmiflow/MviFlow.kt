package com.moon.moonweather.vmiflow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow


interface Store<Wish, State> : FlowCollector<Wish>, Flow<State> {
    val state: State
}

interface Feature<Wish, State, News> : Store<Wish, State> {
    val news: Flow<News>
}

typealias Bootstrapper<Action> = () -> Flow<Action>

typealias WishToAction<Wish, Action> = (wish: Wish) -> Action

typealias Actor<State, Action, Effect> = (state: State, action: Action) -> Flow<Effect>

typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State

typealias PostProcessor<Action, Effect, State> =
            (action: Action, effect: Effect, state: State) -> Action?

typealias NewsPublisher<Action, Effect, State, News> =
            (action: Action, effect: Effect, state: State) -> News?

class BaseFeature<Wish, Action, Effect, State, News>(
    initialState: State,
    bootstrapper: Bootstrapper<Action>? = null,
    private val wishToAction: WishToAction<Wish, Action>,
    actor: Actor<State, Action, Effect>,
    reducer: Reducer<State, Effect>,
    postprocessor: PostProcessor<Action, Effect, State>? = null,
    newsPublisher: NewsPublisher<Action, Effect, State, News>? = null,
) : Feature<Wish, State, News> {

    private val stateSubject = MutableStateFlow(initialState)
    private val actionSubject = BroadcastChannel<Action>(1)
    private val newsSubject = BroadcastChannel<News>(1)

    override val state: State
        get() = stateSubject.value

    override val news: Flow<News>
        get() = newsSubject.asFlow()

    override suspend fun emit(value: Wish) {

        val action = wishToAction(value)
        actionSubject.send(action)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<State>) {
        stateSubject.collect(collector)
    }

}