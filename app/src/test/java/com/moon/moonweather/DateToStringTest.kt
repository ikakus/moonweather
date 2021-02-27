package com.moon.moonweather

import com.moon.moonweather.feature.forecast.utils.DateToString
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateToStringTest {

    lateinit var dateToString: DateToString

    @Before
    fun setup() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val current = format.parse("2021-02-24")
        dateToString = DateToString(current)
    }

    @Test
    fun `return Today if current date matches`() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val current = format.parse("2021-02-24")
        dateToString = DateToString(current)
        val result = dateToString.parseDate("2021-02-24")
        assertThat(result, equalTo("Today"))
    }

    @Test
    fun `return Tomorrow if current date is one day older`() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val current = format.parse("2021-02-24")
        dateToString = DateToString(current)
        val result = dateToString.parseDate("2021-02-25")
        assertThat(result, equalTo("Tomorrow"))
    }

    @Test
    fun `return Formatted date if current date is more than 2 days older`() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val current = format.parse("2021-02-24")
        dateToString = DateToString(current)
        val result = dateToString.parseDate("2021-02-26")
        assertThat(result, equalTo("26 February"))
    }
}