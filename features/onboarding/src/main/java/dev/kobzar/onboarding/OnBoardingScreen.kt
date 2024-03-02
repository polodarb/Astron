package dev.kobzar.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.ui.compose.theme.AppTheme

class OnBoardingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val asteroidsListScreen = rememberScreen(SharedScreen.AsteroidsListScreen)
        OnBoardingScreenComposable(
            navigator = navigator,
            screen = asteroidsListScreen
        )
    }

}

@Composable
private fun OnBoardingScreenComposable(
    navigator: Navigator?,
    screen: Screen
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "OnBoardingScreen()", style = AppTheme.typography.body)
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = {
                navigator?.push(screen)
            }) {
                Text(text = "To AsteroidsListScreen")
            }
        }
    }
}