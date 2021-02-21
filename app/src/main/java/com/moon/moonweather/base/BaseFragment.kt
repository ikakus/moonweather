package com.moon.moonweather.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject

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
