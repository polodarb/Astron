package dev.kobzar.details

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
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.ui.compose.theme.AppTheme

data class DetailsScreen(
    val asteroidId: String?
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val compareScreen = rememberScreen(SharedScreen.CompareScreen)

        DetailsScreenComposable(
            asteroidId = asteroidId,
            onBackClick = { navigator?.pop() },
            onCompareClick = { navigator?.push(compareScreen) }
        )
    }

}

@Composable
private fun DetailsScreenComposable(
    asteroidId: String?,
    onBackClick: () -> Unit,
    onCompareClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "DetailsScreen\nparam: $asteroidId", style = AppTheme.typography.subtitle)
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = onBackClick) {
                Text(text = "Back")

            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onCompareClick) {
                Text(text = "To CompareScreen")
            }
        }
    }
}