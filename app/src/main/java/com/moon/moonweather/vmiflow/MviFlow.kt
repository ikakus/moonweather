package com.moon.moonweather.vmiflow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
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
    actor: Actor<State, Action, Effect>,
    reducer: Reducer<State, Effect>,
    postprocessor: PostProcessor<Action, Effect, State>? = null,
    newsPublisher: NewsPublisher<Action, Effect, State, News>? = null,
) : Feature<Wish, State, News> {

    private val stateSubject = MutableStateFlow(initialState)
    private val actionSubject = BroadcastChannel<Action>(1)
    private val newsSubject = BroadcastChannel<News>(1)

    private val newsPublisherWrapper = NewPublisherWrapper(newsPublisher, newsSubject)
    private val postProcessorWrapper = PostProcessorWrapper(postprocessor, actionSubject)
    private val reducerWrapper =
        ReducerWrapper(reducer, stateSubject, postProcessorWrapper, newsPublisherWrapper)
    private val actorWrapper = ActorWrapper(actor, reducerWrapper)

    init {
        GlobalScope.launch {
            actionSubject.asFlow().collect { action ->
                actorWrapper.emit(Pair(state, action))
            }
        }
        GlobalScope.launch {
            actionSubject.let { output ->
                bootstrapper?.invoke()?.collect {
                    output.send(it)
                }
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
        private val reducerWrapper: ReducerWrapper<State, Action, Effect>
    ) : FlowCollector<Pair<State, Action>> {

        override suspend fun emit(value: Pair<State, Action>) {
            val (state, action) = value
            actor(state, action).collect { effect ->
                reducerWrapper.emit(Triple(state, action, effect))
            }
        }
    }

    class ReducerWrapper<State, Action, Effect>(
        private val reducer: Reducer<State, Effect>,
        private val stateSubject: MutableStateFlow<State>,
        private val postProcessorWrapper: FlowCollector<Triple<Action, Effect, State>>,
        private val newsPublisherWrapper: FlowCollector<Triple<Action, Effect, State>>,
    ) : FlowCollector<Triple<State, Action, Effect>> {

        override suspend fun emit(value: Triple<State, Action, Effect>) {
            val (state, action, effect) = value
            val st = reducer.invoke(state, effect)
            stateSubject.emit(st)
            postProcessorWrapper.emit(Triple(action, effect, state))
            newsPublisherWrapper.emit(Triple(action, effect, state))
        }
    }

    class PostProcessorWrapper<Action, Effect, State>(
        private val postprocessor: PostProcessor<Action, Effect, State>?,
        private val actionSubject: BroadcastChannel<Action>
    ) : FlowCollector<Triple<Action, Effect, State>> {
        override suspend fun emit(value: Triple<Action, Effect, State>) {
            val (action, effect, state) = value
            postprocessor?.invoke(action, effect, state).let {
                it?.let {
                    actionSubject.send(it)
                }
            }
        }
    }

    class NewPublisherWrapper<Action, Effect, State, News>(
        private val newsPublisher: NewsPublisher<Action, Effect, State, News>?,
        private val newsSubject: BroadcastChannel<News>
    ) : FlowCollector<Triple<Action, Effect, State>> {
        override suspend fun emit(value: Triple<Action, Effect, State>) {
            val (action, effect, state) = value
            newsPublisher?.invoke(action, effect, state).let {
                it?.let {
                    newsSubject.send(it)
                }
            }
        }

    }
}