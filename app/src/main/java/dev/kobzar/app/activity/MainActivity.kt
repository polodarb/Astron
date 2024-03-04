package dev.kobzar.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.compare.CompareScreen
import dev.kobzar.details.DetailsScreen
import dev.kobzar.favorites.FavoritesScreen
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.settings.SettingsScreen
import dev.kobzar.ui.compose.AppTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenRegistry {
            register<SharedScreen.OnBoardingScreen> { OnBoardingScreen() }
            register<SharedScreen.AsteroidsListScreen> { AsteroidsListScreen() }
            register<SharedScreen.DetailsScreen> { DetailsScreen(asteroidId = null) }
            register<SharedScreen.FavoritesScreen> { FavoritesScreen() }
            register<SharedScreen.SettingsScreen> { SettingsScreen() }
            register<SharedScreen.CompareScreen> { CompareScreen() }
        }

        setContent {
            AppTheme {
                Navigator(screen = OnBoardingScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}
