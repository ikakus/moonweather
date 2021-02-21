package com.moon.moonweather.core.di

import java.lang.ref.WeakReference

abstract class DaggerComponentProvider<T> {
    private var component: T? = null
    private var subScopes: MutableSet<WeakReference<DaggerComponentProvider<*>>> = mutableSetOf()

    fun get(): T {
        if (component == null) {
            component = buildComponent()
        }
        return component!!
    }

    fun dependAndGet(subScope: DaggerComponentProvider<*>): T {
        subScopes.add(WeakReference(subScope))
        return get()
    }

    protected abstract fun buildComponent(): T

    fun destroy() {
        subScopes.forEach { it.get()?.destroy() }
        subScopes.clear()
        component = null
    }
}