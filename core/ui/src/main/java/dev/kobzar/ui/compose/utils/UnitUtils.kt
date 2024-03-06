package dev.kobzar.ui.compose.utils

import java.math.BigDecimal
import java.math.RoundingMode

object UnitUtils {

    fun roundDouble(input: Double): String {
        val bd = BigDecimal(input)
        val truncatedValue = bd.setScale(3, RoundingMode.HALF_UP)
        return truncatedValue.toString()
    }

}