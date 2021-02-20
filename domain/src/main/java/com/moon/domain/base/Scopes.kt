package com.moon.domain.base

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
