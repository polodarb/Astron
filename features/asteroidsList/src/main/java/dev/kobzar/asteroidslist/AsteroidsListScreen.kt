package dev.kobzar.asteroidslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import dev.kobzar.ui.compose.theme.AppTheme

class AsteroidsListScreen: Screen {

    @Composable
    override fun Content() = AsteroidsListScreenComposable()

}

@Composable
private fun AsteroidsListScreenComposable() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AsteroidsListScreen()", style = AppTheme.typography.body)
    }
}