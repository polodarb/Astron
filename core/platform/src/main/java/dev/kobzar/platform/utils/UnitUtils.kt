package dev.kobzar.platform.utils

import java.math.RoundingMode

object UnitUtils {

    fun roundDouble(input: Double, digits: Int = 3): Double {
        return input.toBigDecimal().setScale(digits, RoundingMode.HALF_UP).toDouble()
    }

    fun extractIntegerPart(input: String): String {
        val decimalIndex = input.indexOf('.')
        return if (decimalIndex != -1) {
            input.substring(0, decimalIndex)
        } else {
            input
        }
    }

}