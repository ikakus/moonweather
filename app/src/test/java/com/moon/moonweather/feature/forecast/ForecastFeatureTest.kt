package com.moon.moonweather.feature.forecast

import com.badoo.mvicore.extension.SameThreadVerifier
import com.moon.data.forecast.model.PlaceEntity
import com.moon.domain.forecast.model.DayDomainModel
import com.moon.domain.forecast.model.ForecastDomainModel
import com.moon.domain.forecast.model.ForecastListDomainModel
import com.moon.domain.forecast.usecase.GetForecastUseCase
import com.moon.moonweather.core.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ForecastFeatureTest {

    @Mock
    private lateinit var getForecastUseCase: GetForecastUseCase

    private lateinit var states: TestObserver<ForecastFeature.State>
    private lateinit var feature: ForecastFeature

    private val schedulerProvider = TestSchedulerProvider()


    @Before
    fun prepare() {

        MockitoAnnotations.initMocks(this)
        SameThreadVerifier.isEnabled = false

        feature = ForecastFeature(schedulerProvider, getForecastUseCase)

        val subscription = PublishSubject.create<ForecastFeature.State>()
        states = subscription.test()

        Observable.wrap(feature).subscribe {
            subscription.onNext(it)
        }
    }

    @Test
    fun `if there are no wishes, feature only emits initial state`() {
        assertThat(states.onNextEvents().size, equalTo(1))
    }

    @Test
    fun `emitted initial state is correct`() {
        val state: ForecastFeature.State = states.onNextEvents().first()
        assertThat(state.loading, equalTo(false))
        assertThat(state.forecastData, equalTo(null))
    }

    @Test
    fun `emit forecast on LoadDataWish`() {
        val forecastDomainModel = mock<ForecastDomainModel>()
        val forecastList = listOf(forecastDomainModel, forecastDomainModel)
        val forecastListDomainModel = mock<ForecastListDomainModel> {
            on { forecasts } doReturn forecastList
        }

        whenever(getForecastUseCase()) doReturn Single.just(forecastListDomainModel)
        val feature = ForecastFeature(schedulerProvider, getForecastUseCase)

        val subscription = PublishSubject.create<ForecastFeature.State>()
        val states = subscription.test()

        Observable.wrap(feature).subscribe {
            subscription.onNext(it)
        }

        feature.accept(ForecastFeature.Wish.LoadData)

        val state = states.onNextEvents().last()
        assertThat(state.forecastData, equalTo(forecastList))
    }

    @Test
    fun `emit PlaceWeatherDetails on ShowPlaceDetails`() {

        val placeModel = mock<PlaceEntity> {
            on { name } doReturn "test"
        }

        val dayModel = mock<DayDomainModel> {
            on { places } doReturn listOf(placeModel)
        }
        val forecastDomainModel = mock<ForecastDomainModel>() {
            on { day } doReturn dayModel
            on { night } doReturn dayModel
        }
        val forecastList = listOf(forecastDomainModel)
        val forecastListDomainModel = mock<ForecastListDomainModel> {
            on { forecasts } doReturn forecastList
        }

        whenever(getForecastUseCase()) doReturn Single.just(forecastListDomainModel)

        val feature = ForecastFeature(schedulerProvider, getForecastUseCase)

        var newsItem: ForecastFeature.News? = null
        Observable.wrap(feature.news).subscribe {
            newsItem = it
        }

        feature.accept(ForecastFeature.Wish.ShowPlaceDetails("test"))

        assertThat(newsItem, instanceOf(ForecastFeature.News.PlaceWeatherDetails::class.java))
    }
}

fun <T> TestObserver<T>.onNextEvents(): MutableList<T> = values()
