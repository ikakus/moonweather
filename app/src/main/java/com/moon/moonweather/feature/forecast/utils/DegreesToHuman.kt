package com.moon.moonweather.feature.forecast.utils

class DegreesToHuman {

    fun getString(input: String): String {
        var formattedInput = input
        val prefix = if (input[0] == '-') {
            formattedInput = input.replace("-", "")
            "minus "
        } else {
            ""
        }


        val result = when {
            formattedInput.length == 1 -> getSimpleWord(formattedInput)
            formattedInput.first() == '1' -> getTensUnderTwentyWords(formattedInput)
            else -> getComplexTensWords(formattedInput)
        }
        return (prefix + result).capitalize()
    }

    fun getStringWithPostfix(input: String): String {
        return getString(input) + " " + getDegreePostfix(input)
    }

    private fun getDegreePostfix(formattedInput: String): String {
        return when {
            formattedInput.last() == '1' -> "degree"
            else -> "degrees"
        }
    }


    private fun getComplexTensWords(input: String): String {
        val firstChar = input[0]
        val secondChar = input[1]
        if (secondChar == '0') return getTensWords(firstChar.toString())
        return getTensWords(firstChar.toString()) + " " + getSimpleWord(secondChar.toString())
    }

    private fun getTensWords(firstDecimal: String): String {
        return when (firstDecimal) {
            "2" -> "twenty"
            "3" -> "thirty"
            "4" -> "forty"
            "5" -> "fifty"
            "6" -> "sixty"
            "7" -> "seventy"
            else -> ""
        }
    }

    private fun getTensUnderTwentyWords(input: String): String {
        return when (input) {
            "10" -> "ten"
            "11" -> "eleven"
            "12" -> "twelve"
            "13" -> "thirteen"
            "14" -> "fourteen"
            "15" -> "fifteen"
            "16" -> "sixteen"
            "17" -> "seventeen"
            "18" -> "eighteen"
            "19" -> "nineteen"
            else -> ""
        }
    }

    private fun getSimpleWord(input: String): String {
        return when (input) {
            "0" -> "zero"
            "1" -> "one"
            "2" -> "two"
            "3" -> "three"
            "4" -> "four"
            "5" -> "five"
            "6" -> "six"
            "7" -> "seven"
            "8" -> "eight"
            "9" -> "nine"
            else -> ""
        }
    }
}