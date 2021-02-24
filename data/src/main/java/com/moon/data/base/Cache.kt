package com.moon.data.base

interface Cache<T> {
    fun put(data: T)
    fun get(): T?
}