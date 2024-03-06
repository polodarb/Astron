package dev.kobzar.compare

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.ui.compose.theme.AppTheme

class CompareScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        CompareScreenComposable(
            onReturnToMain = {
                navigator?.popUntilRoot()
            }
        )
    }

}

@Composable
private fun CompareScreenComposable(
    onReturnToMain: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "CompareScreen", style = AppTheme.typography.medium16)
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = onReturnToMain) {
                Text(text = "To MainScreen")
            }
        }
    }
}