package dev.kobzar.platform.utils

import java.math.BigDecimal
import java.math.RoundingMode

object UnitUtils {

    fun roundDouble(input: Double, digits: Int = 3): Double {
        return try {
            val bd = BigDecimal(input)
            val truncatedValue = bd.setScale(digits, RoundingMode.HALF_UP)
            return truncatedValue.toDouble()
        } catch (e: Exception) {
            0.0
        }
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