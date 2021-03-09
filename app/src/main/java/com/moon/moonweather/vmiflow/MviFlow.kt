package com.moon.moonweather.vmiflow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


interface Store<Wish, State> : FlowCollector<Wish>, Flow<State> {
    val state: State
}

interface Feature<Wish, State, News> : Store<Wish, State> {
    val news: Flow<News>
}

