package com.moon.moonweather.vmiflow

import com.moon.moonweather.feature.forecast.ForecastFeature
import com.moon.moonweather.feature.forecast.NewsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@InternalCoroutinesApi
abstract class Binder<U> {
    var jobs = HashSet<Job>()
    protected lateinit var coroutineScope: CoroutineScope
    abstract fun setup(uiComponent: U)

    protected fun <Out : Any, In : Any> bind(connection: Triple<Flow<Out>, FlowCollector<In>, (Out) -> In?>) {
        val (flow, collector, transformer) = connection
        val job = coroutineScope.launch {
            flow.map { value -> transformer(value) }
                .filterNotNull()
                .collect(collector)

        }
        jobs.add(job)
    }


    protected fun bind(connection: Pair<Flow<ForecastFeature.News>, NewsListener>) {
        val (flow, collector) = connection
        val job = coroutineScope.launch {
            flow.filterNotNull()
                .collect(collector)

        }
        jobs.add(job)
    }

    protected fun cancel() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    protected infix fun <Out, In> Pair<Flow<Out>, FlowCollector<In>>.using(transformer: (Out) -> In?) =
        Triple(first, second, transformer)
}
