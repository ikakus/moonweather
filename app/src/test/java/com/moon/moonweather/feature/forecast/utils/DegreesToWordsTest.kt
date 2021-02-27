package com.moon.moonweather.feature.forecast.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class DegreesToWordsTest {

    lateinit var degreesToWords: DegreesToHuman

    @Before
    fun setUp() {
        degreesToWords = DegreesToHuman()
    }

    @Test
    fun `simple numbers conversion`() {
        assertThat(degreesToWords.getString("0"), equalTo("Zero"))
        assertThat(degreesToWords.getString("1"), equalTo("One"))
        assertThat(degreesToWords.getString("2"), equalTo("Two"))
        assertThat(degreesToWords.getString("3"), equalTo("Three"))
        assertThat(degreesToWords.getString("4"), equalTo("Four"))
        assertThat(degreesToWords.getString("5"), equalTo("Five"))
        assertThat(degreesToWords.getString("6"), equalTo("Six"))
        assertThat(degreesToWords.getString("7"), equalTo("Seven"))
        assertThat(degreesToWords.getString("8"), equalTo("Eight"))
        assertThat(degreesToWords.getString("9"), equalTo("Nine"))
    }

    @Test
    fun `simple tens  under 20 numbers conversion`() {
        assertThat(degreesToWords.getString("10"), equalTo("Ten"))
        assertThat(degreesToWords.getString("11"), equalTo("Eleven"))
        assertThat(degreesToWords.getString("12"), equalTo("Twelve"))
        assertThat(degreesToWords.getString("13"), equalTo("Thirteen"))
        assertThat(degreesToWords.getString("14"), equalTo("Fourteen"))
        assertThat(degreesToWords.getString("15"), equalTo("Fifteen"))
        assertThat(degreesToWords.getString("16"), equalTo("Sixteen"))
        assertThat(degreesToWords.getString("17"), equalTo("Seventeen"))
        assertThat(degreesToWords.getString("18"), equalTo("Eighteen"))
        assertThat(degreesToWords.getString("19"), equalTo("Nineteen"))
    }

    @Test
    fun `simple tens numbers conversion`() {
        assertThat(degreesToWords.getString("20"), equalTo("Twenty"))
        assertThat(degreesToWords.getString("30"), equalTo("Thirty"))
        assertThat(degreesToWords.getString("40"), equalTo("Forty"))
        assertThat(degreesToWords.getString("50"), equalTo("Fifty"))
        assertThat(degreesToWords.getString("60"), equalTo("Sixty"))
        assertThat(degreesToWords.getString("70"), equalTo("Seventy"))
    }

    @Test
    fun `tens numbers conversion`() {
        assertThat(degreesToWords.getString("21"), equalTo("Twenty one"))
        assertThat(degreesToWords.getString("32"), equalTo("Thirty two"))
        assertThat(degreesToWords.getString("43"), equalTo("Forty three"))
        assertThat(degreesToWords.getString("54"), equalTo("Fifty four"))
        assertThat(degreesToWords.getString("65"), equalTo("Sixty five"))
        assertThat(degreesToWords.getString("76"), equalTo("Seventy six"))
    }

    @Test
    fun `add Minus word for negative temperatures`() {
        assertThat(degreesToWords.getString("-21"), equalTo("Minus twenty one"))
        assertThat(degreesToWords.getString("-32"), equalTo("Minus thirty two"))
        assertThat(degreesToWords.getString("-43"), equalTo("Minus forty three"))
    }

    @Test
    fun `add degree word postfix`() {
        assertThat(degreesToWords.getStringWithPostfix("21"), equalTo("Twenty one degree"))
        assertThat(degreesToWords.getStringWithPostfix("0"), equalTo("Zero degrees"))
        assertThat(degreesToWords.getStringWithPostfix("1"), equalTo("One degree"))
        assertThat(degreesToWords.getStringWithPostfix("2"), equalTo("Two degrees"))
        assertThat(degreesToWords.getStringWithPostfix("22"), equalTo("Twenty two degrees"))
    }
}