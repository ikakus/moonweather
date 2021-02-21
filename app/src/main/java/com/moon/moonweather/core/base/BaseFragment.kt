package com.moon.moonweather.core.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

abstract class BaseFragment<Event, Model>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId),
    ObservableSource<Event>,
    Consumer<Model> {

    private val source = PublishSubject.create<Event>()

    protected fun uiEvent(event: Event) {
        source.onNext(event)
    }

    override fun subscribe(observer: Observer<in Event>) {
        source.subscribe(observer)
    }
}
