package com.moon.moonweather.vmiflow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*


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

@FlowPreview
@ExperimentalCoroutinesApi
open class BaseFlowFeature<Wish, Action, Effect, State, News>(
    initialState: State,
    bootstrapper: Bootstrapper<Action>? = null,
    private val wishToAction: WishToAction<Wish, Action>,
    private val actor: Actor<State, Action, Effect>,
    reducer: Reducer<State, Effect>,
    postprocessor: PostProcessor<Action, Effect, State>? = null,
    newsPublisher: NewsPublisher<Action, Effect, State, News>? = null,
) : Feature<Wish, State, News> {

    private val stateSubject = MutableStateFlow(initialState)
    private val actionSubject = ConflatedBroadcastChannel<Action>()
    private val newsSubject = BroadcastChannel<News>(1)

    private val actorWrapper = ActorWrapper(actor, reducer)

    init {
        GlobalScope.launch {
            actionSubject.asFlow().collect {
                actorWrapper.emit(Pair(state, it))
            }
        }

    }

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

    class ActorWrapper<State, Action, Effect>(
        private val actor: Actor<State, Action, Effect>,
        private val reducer: Reducer<State, Effect>,

        ) : FlowCollector<Pair<State, Action>> {
        override suspend fun emit(value: Pair<State, Action>) {
            val (state, action) = value
            actor(state, action).collect {
                reducer.invoke(state, it)
            }
        }

    }
}