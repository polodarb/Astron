package dev.kobzar.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.ui.compose.AppTheme

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenRegistry {
            register<SharedScreen.OnBoardingScreen> { OnBoardingScreen() }
            register<SharedScreen.AsteroidsListScreen> { AsteroidsListScreen() }
//            register<SharedScreen.MovieScreen> { provider -> MovieScreen(movieLink = provider.movieLink) }
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
