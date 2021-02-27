package com.moon.moonweather.feature.forecast

import java.text.SimpleDateFormat
import java.util.*

class DateToString(private val currentDate: Date) {
    fun parseDate(input: String): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val inputDate = inputFormat.parse(input)!!

        val calInput = Calendar.getInstance()
        val calCurrent = Calendar.getInstance()

        calInput.time = inputDate
        calCurrent.time = currentDate

        // Today
        if (
            calInput.get(Calendar.DAY_OF_MONTH) ==
            calCurrent.get(Calendar.DAY_OF_MONTH)
        ) return "Today"

        // Tomorrow

        calCurrent.add(Calendar.DAY_OF_MONTH, 1)

        if (
            calInput.get(Calendar.DAY_OF_MONTH) ==
            calCurrent.get(Calendar.DAY_OF_MONTH)
        ) return "Tomorrow"

        // Other
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.ROOT)
        return outputFormat.format(inputDate)
    }
}