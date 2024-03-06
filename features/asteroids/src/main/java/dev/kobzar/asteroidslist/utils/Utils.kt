package dev.kobzar.asteroidslist.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

object Utils {

    fun formatDateFromTimestamp(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.ofEpochDay(timestamp / 86400000).format(formatter)
    }

    fun getDaysDifference(date1: String, date2: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate1 = LocalDate.parse(date1, formatter)
        val localDate2 = LocalDate.parse(date2, formatter)
        return ChronoUnit.DAYS.between(localDate1, localDate2).absoluteValue.toInt()
    }

}