package dev.kobzar.ui.compose.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import dev.kobzar.ui.compose.lightColors

class Colors(
    background: Color,
    backgroundSurface: Color,
    primary: Color,
    secondaryGray: Color,
    secondaryGray900: Color,
    secondaryGray800: Color,
    secondaryGray700: Color,
    secondaryGray600: Color,
    secondaryGray500: Color,
    secondaryGray400: Color,
    secondaryGray300: Color,
    secondaryGray200: Color,
    secondaryGray100: Color,
    secondaryGray50: Color,
    secondaryBlue900: Color,
    secondaryBlue600: Color,
    secondaryBlue500: Color,
    secondaryBlue400: Color,
    secondaryBlue200: Color,
    secondaryBlue100: Color,
    secondaryLightBlue50: Color,
    darkBlue500: Color,
    darkBlue700: Color,
    darkBlue900: Color,
    secondaryGreen: Color,
    secondaryGreen700: Color,
    secondaryGreen300: Color,
    secondaryGreen100: Color,
    secondaryRed: Color,
    secondaryRed700: Color,
    secondaryRed600: Color,
    secondaryRed300: Color,
    secondaryRed200: Color,
    secondaryRed100: Color,
    tertiaryViolet900: Color,
    tertiaryViolet800: Color,
    tertiaryViolet700: Color,
    tertiaryViolet600: Color,
    tertiaryViolet300: Color,
    tertiaryViolet200: Color,
    tertiaryViolet100: Color,
    tertiaryViolet50: Color,
    border: Color,
    outline: Color,
    white: Color,
    black: Color
) {

    var background by mutableStateOf(background)
        private set

    var backgroundSurface by mutableStateOf(backgroundSurface)
        private set

    var primary by mutableStateOf(primary)
        private set

    var secondaryGray by mutableStateOf(secondaryGray)
        private set

    var secondaryGray900 by mutableStateOf(secondaryGray900)
        private set

    var secondaryGray800 by mutableStateOf(secondaryGray800)
        private set

    var secondaryGray700 by mutableStateOf(secondaryGray700)
        private set

    var secondaryGray600 by mutableStateOf(secondaryGray600)
        private set

    var secondaryGray500 by mutableStateOf(secondaryGray500)
        private set

    var secondaryGray400 by mutableStateOf(secondaryGray400)
        private set

    var secondaryGray300 by mutableStateOf(secondaryGray300)
        private set

    var secondaryGray200 by mutableStateOf(secondaryGray200)
        private set

    var secondaryGray100 by mutableStateOf(secondaryGray100)
        private set

    var secondaryGray50 by mutableStateOf(secondaryGray50)
        private set

    var secondaryBlue900 by mutableStateOf(secondaryBlue900)
        private set

    var secondaryBlue600 by mutableStateOf(secondaryBlue600)
        private set

    var secondaryBlue500 by mutableStateOf(secondaryBlue500)
        private set

    var secondaryBlue400 by mutableStateOf(secondaryBlue400)
        private set

    var secondaryBlue200 by mutableStateOf(secondaryBlue200)
        private set

    var secondaryBlue100 by mutableStateOf(secondaryBlue100)
        private set

    var secondaryLightBlue50 by mutableStateOf(secondaryLightBlue50)
        private set

    var darkBlue500 by mutableStateOf(darkBlue500)
        private set

    var darkBlue700 by mutableStateOf(darkBlue700)
        private set

    var darkBlue900 by mutableStateOf(darkBlue900)
        private set

    var secondaryGreen by mutableStateOf(secondaryGreen)
        private set

    var secondaryGreen700 by mutableStateOf(secondaryGreen700)
        private set

    var secondaryGreen300 by mutableStateOf(secondaryGreen300)
        private set

    var secondaryGreen100 by mutableStateOf(secondaryGreen100)
        private set

    var secondaryRed by mutableStateOf(secondaryRed)
        private set

    var secondaryRed700 by mutableStateOf(secondaryRed700)
        private set

    var secondaryRed600 by mutableStateOf(secondaryRed600)
        private set

    var secondaryRed300 by mutableStateOf(secondaryRed300)
        private set

    var secondaryRed200 by mutableStateOf(secondaryRed200)
        private set

    var secondaryRed100 by mutableStateOf(secondaryRed100)
        private set

    var tertiaryViolet900 by mutableStateOf(tertiaryViolet900)
        private set

    var tertiaryViolet800 by mutableStateOf(tertiaryViolet800)
        private set

    var tertiaryViolet700 by mutableStateOf(tertiaryViolet700)
        private set

    var tertiaryViolet600 by mutableStateOf(tertiaryViolet600)
        private set

    var tertiaryViolet300 by mutableStateOf(tertiaryViolet300)
        private set

    var tertiaryViolet200 by mutableStateOf(tertiaryViolet200)
        private set

    var tertiaryViolet100 by mutableStateOf(tertiaryViolet100)
        private set

    var tertiaryViolet50 by mutableStateOf(tertiaryViolet50)
        private set

    var border by mutableStateOf(border)
        private set

    var outline by mutableStateOf(outline)
        private set

    var white by mutableStateOf(white)
        private set

    var black by mutableStateOf(black)
        private set
}

val LocalColors = staticCompositionLocalOf { lightColors() }