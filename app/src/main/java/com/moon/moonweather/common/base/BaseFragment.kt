package com.moon.moonweather.common.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow

abstract class BaseFragment<Event, Model>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId),
    Flow<Event>,
    FlowCollector<Model> {

    private val uiModelsChannel = BroadcastChannel<Model>(1)
    protected val uiEvents = BroadcastChannel<Event>(1)

    override suspend fun emit(uiModel: Model) {
        uiModelsChannel.send(uiModel)
    }

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<Event>) {
        uiEvents.asFlow().collect(collector)
    }
}
