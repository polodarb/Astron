package dev.kobzar.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import dev.kobzar.ui.compose.theme.AppTheme

class SettingsScreen : Screen {

    @Composable
    override fun Content() = SettingsScreenComposable()

}

@Composable
private fun SettingsScreenComposable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "SettingsScreen", style = AppTheme.typography.medium16)
    }
}