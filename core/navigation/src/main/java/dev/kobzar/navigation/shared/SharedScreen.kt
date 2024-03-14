package dev.kobzar.navigation.shared

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    object OnBoardingScreen : SharedScreen()
    object AsteroidsListScreen : SharedScreen()
    object FavoritesScreen : SharedScreen()
    data class DetailsScreen(val asteroidId: String?, val isDistanceDanger: Boolean = false) : SharedScreen()
    object SettingsScreen : SharedScreen()
    object CompareScreen : SharedScreen()
}