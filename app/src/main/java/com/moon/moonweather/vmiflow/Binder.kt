package com.moon.moonweather.vmiflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

typealias Connection<In, Out> = Triple<StateFlow<Out>, FlowCollector<In>, (Out) -> In?>

abstract class Bindings<U>(private val coroutineScope: CoroutineScope) {
    abstract fun setup(uiComponent: U)

    protected fun <Out : Any, In : Any> bind(connection: Connection<In, Out>) {
        val (flow, collector, transformer) = connection
        coroutineScope.launch {
            flow.map { value -> transformer(value) }
                .filterNotNull()
                .collect {
                    collector.emit(it)
                }
        }
    }

    protected infix fun <Out, In> Pair<StateFlow<Out>, FlowCollector<In>>.using(transformer: (Out) -> In?) =
        Connection(first, second, transformer)
}