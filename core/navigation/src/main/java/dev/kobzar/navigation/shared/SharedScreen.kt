package dev.kobzar.navigation.shared

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    object OnBoardingScreen : SharedScreen()
    object AsteroidsListScreen : SharedScreen()
//    data class ScreenWithParam(val param: String) : SharedScreen()
}