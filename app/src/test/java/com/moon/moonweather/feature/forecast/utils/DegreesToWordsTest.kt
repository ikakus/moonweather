package com.moon.moonweather.feature.forecast.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class DegreesToWordsTest {

    lateinit var degreesToWords: DegreesToWords

    @Before
    fun setUp() {
        degreesToWords = DegreesToWords()
    }

    @Test
    fun `simple numbers conversion`() {
        assertThat(degreesToWords.getHumanString("0"), equalTo("Zero"))
        assertThat(degreesToWords.getHumanString("1"), equalTo("One"))
        assertThat(degreesToWords.getHumanString("2"), equalTo("Two"))
        assertThat(degreesToWords.getHumanString("3"), equalTo("Three"))
        assertThat(degreesToWords.getHumanString("4"), equalTo("Four"))
        assertThat(degreesToWords.getHumanString("5"), equalTo("Five"))
        assertThat(degreesToWords.getHumanString("6"), equalTo("Six"))
        assertThat(degreesToWords.getHumanString("7"), equalTo("Seven"))
        assertThat(degreesToWords.getHumanString("8"), equalTo("Eight"))
        assertThat(degreesToWords.getHumanString("9"), equalTo("Nine"))
    }

    @Test
    fun `simple tens  under 20 numbers conversion`() {
        assertThat(degreesToWords.getHumanString("10"), equalTo("Ten"))
        assertThat(degreesToWords.getHumanString("11"), equalTo("Eleven"))
        assertThat(degreesToWords.getHumanString("12"), equalTo("Twelve"))
        assertThat(degreesToWords.getHumanString("13"), equalTo("Thirteen"))
        assertThat(degreesToWords.getHumanString("14"), equalTo("Fourteen"))
        assertThat(degreesToWords.getHumanString("15"), equalTo("Fifteen"))
        assertThat(degreesToWords.getHumanString("16"), equalTo("Sixteen"))
        assertThat(degreesToWords.getHumanString("17"), equalTo("Seventeen"))
        assertThat(degreesToWords.getHumanString("18"), equalTo("Eighteen"))
        assertThat(degreesToWords.getHumanString("19"), equalTo("Nineteen"))
    }

    @Test
    fun `simple tens numbers conversion`() {
        assertThat(degreesToWords.getHumanString("20"), equalTo("Twenty"))
        assertThat(degreesToWords.getHumanString("30"), equalTo("Thirty"))
        assertThat(degreesToWords.getHumanString("40"), equalTo("Forty"))
        assertThat(degreesToWords.getHumanString("50"), equalTo("Fifty"))
        assertThat(degreesToWords.getHumanString("60"), equalTo("Sixty"))
        assertThat(degreesToWords.getHumanString("70"), equalTo("Seventy"))
    }
}