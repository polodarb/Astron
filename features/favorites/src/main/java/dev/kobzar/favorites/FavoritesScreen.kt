package dev.kobzar.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import dev.kobzar.ui.compose.theme.AppTheme

class FavoritesScreen: Screen {

    @Composable
    override fun Content() = FavoritesScreenComposable()

}

@Composable
private fun FavoritesScreenComposable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "FavoritesScreen", style = AppTheme.typography.subtitle)
    }
}