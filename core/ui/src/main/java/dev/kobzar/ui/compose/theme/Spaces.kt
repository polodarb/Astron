package dev.kobzar.ui.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spaces(
    val space2: Dp = 2.dp,
    val space4: Dp = 4.dp,
    val space6: Dp = 6.dp,
    val space8: Dp = 8.dp,
    val space10: Dp = 10.dp,
    val space12: Dp = 12.dp,
    val space14: Dp = 14.dp,
    val space16: Dp = 16.dp,
    val space18: Dp = 18.dp,
    val space20: Dp = 20.dp,
    val space22: Dp = 22.dp,
    val space24: Dp = 24.dp,
    val space26: Dp = 26.dp,
    val space28: Dp = 28.dp,
    val space30: Dp = 30.dp,
    val space32: Dp = 32.dp,
    val space34: Dp = 34.dp,
    val space36: Dp = 36.dp
)

val LocalSpaces = staticCompositionLocalOf { Spaces() }
