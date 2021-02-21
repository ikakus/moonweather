package com.moon.moonweather.core.di

import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Application

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Feature

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Screen
