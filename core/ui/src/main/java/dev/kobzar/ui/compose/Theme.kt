package dev.kobzar.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import dev.kobzar.ui.compose.theme.Colors
import dev.kobzar.ui.compose.theme.LocalColors
import dev.kobzar.ui.compose.theme.LocalSpaces
import dev.kobzar.ui.compose.theme.LocalTypography
import dev.kobzar.ui.compose.theme.Spaces
import dev.kobzar.ui.compose.theme.AppTheme
import dev.kobzar.ui.compose.theme.Typography

fun lightColors() = Colors(
    background = Color(0xFFF9FDFF),
    backgroundSurface = Color(0xFFFFFFFF),
    primary = Color(0xFF040A17),
    secondaryGray = Color(0xFF1D222E),
    secondaryGray900 = Color(0xFF343943),
    secondaryGray800 = Color(0xFF4D515A),
    secondaryGray700 = Color(0xFF646870),
    secondaryGray600 = Color(0xFF7C7F85),
    secondaryGray500 = Color(0xFF95979C),
    secondaryGray400 = Color(0xFFADAFB3),
    secondaryGray300 = Color(0xFFC5C6C9),
    secondaryGray200 = Color(0xFFDDDEDF),
    secondaryGray100 = Color(0xFFF1F1F1),
    secondaryGray50 = Color(0xFFFCFCFC),
    secondaryBlue900 = Color(0xFF5582E7),
    secondaryBlue600 = Color(0xFF7FA1ED),
    secondaryBlue500 = Color(0xFFAAC1F3),
    secondaryBlue400 = Color(0xFFBFD0F6),
    secondaryBlue200 = Color(0xFFD4E0F9),
    secondaryBlue100 = Color(0xFFEAEFFC),
    secondaryLightBlue50 = Color(0xFFEAEFFC),
    darkBlue500 = Color(0xFF193B87),
    darkBlue700 = Color(0xFF0D1E44),
    darkBlue900 = Color(0xFF040A17),
    secondaryGreen = Color(0xFF339D66),
    secondaryGreen700 = Color(0xFF83C3A2),
    secondaryGreen300 = Color(0xFFBEE0CF),
    secondaryGreen100 = Color(0xFFE6F3ED),
    secondaryRed = Color(0xFFF84C16),
    secondaryRed700 = Color(0xFFF97045),
    secondaryRed600 = Color(0xFFFB9473),
    secondaryRed300 = Color(0xFFFCB7A2),
    secondaryRed200 = Color(0xFFFEDBD0),
    secondaryRed100 = Color(0xFFFEEDE8),
    tertiaryViolet900 = Color(0xFF5D50BB),
    tertiaryViolet800 = Color(0xFF6A5DC9),
    tertiaryViolet700 = Color(0xFF887DD4),
    tertiaryViolet600 = Color(0xFFA69EDF),
    tertiaryViolet300 = Color(0xFFC3BEE9),
    tertiaryViolet200 = Color(0xFFE1DFF4),
    tertiaryViolet100 = Color(0xFFF0EFFA),
    tertiaryViolet50 = Color(0xFFF9F9FD),
    border = Color(0xFFF9FDFF),
)


@Composable
fun AppTheme(
    spaces: Spaces = AppTheme.spaces,
    typography: Typography = AppTheme.typography,
    content: @Composable () -> Unit,
) {
    val colorsTheme = lightColors()
    CompositionLocalProvider(
        LocalColors provides colorsTheme,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
        content = content
    )
}
