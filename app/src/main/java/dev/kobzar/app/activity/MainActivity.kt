package dev.kobzar.app.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val context: Context by lazy {
        this.applicationContext
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuredFile = File(context.filesDir, "configured")
        val isFirstStart = !configuredFile.exists()


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
                val startScreen = if (isFirstStart) {
                    OnBoardingScreen()
                } else {
                    AsteroidsListScreen()
                }

                Navigator(screen = startScreen) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }

    }
}