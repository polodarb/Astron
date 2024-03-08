package dev.kobzar.platform.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object UnitUtils {

    fun roundDouble(input: Double, digits: Int = 3): Double {
        return try {
            val df = DecimalFormat("#." + "#".repeat(digits))
            df.format(input).toDouble()
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