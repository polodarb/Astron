package dev.kobzar.app.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import dev.kobzar.asteroidslist.AsteroidsListScreen
import dev.kobzar.onboarding.OnBoardingScreen
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.ui.compose.AppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var preferencesRepository: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstStart = runBlocking { preferencesRepository.isFirstStart.first() }

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
