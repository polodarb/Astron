package dev.kobzar.ui.compose.theme.dialogsTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun CustomDialogTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                primary = AppTheme.colors.primary,
                surface = AppTheme.colors.background,
                secondaryContainer = AppTheme.colors.secondaryGray200
            ),
            shapes = MaterialTheme.shapes.copy(
                large = MaterialTheme.shapes.large
            ),
            content = content
        )
    }
}