package dev.kobzar.app

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import dagger.hilt.android.HiltAndroidApp
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.compare.CompareScreen
import dev.kobzar.details.DetailsScreen
import dev.kobzar.favorites.FavoritesScreen
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.settings.SettingsScreen

@HiltAndroidApp
class AsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            register<SharedScreen.OnBoardingScreen> { OnBoardingScreen() }
            register<SharedScreen.AsteroidsListScreen> { AsteroidsListScreen() }
            register<SharedScreen.DetailsScreen> { DetailsScreen(asteroidId = it.asteroidId) }
            register<SharedScreen.FavoritesScreen> { FavoritesScreen() }
            register<SharedScreen.SettingsScreen> { SettingsScreen() }
            register<SharedScreen.CompareScreen> { CompareScreen() }
        }

    }

}