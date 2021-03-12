package com.moon.moonweather.vmiflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

typealias Connection<In, Out> = Triple<Flow<Out>, FlowCollector<In>, (Out) -> In?>

@InternalCoroutinesApi
abstract class Bindings<U>() {
    protected lateinit var coroutineScope: CoroutineScope
    abstract fun setup(uiComponent: U, coroutineScope: CoroutineScope)

    protected fun <Out : Any, In : Any> bind(connection: Connection<In, Out>) {
        val (flow, collector, transformer) = connection
        coroutineScope.launch {
            flow.map { value -> transformer(value) }
                .filterNotNull()
                .collect(collector)

        }
    }

    protected infix fun <Out, In> Pair<Flow<Out>, FlowCollector<In>>.using(transformer: (Out) -> In?) =
        Connection(first, second, transformer)
}
