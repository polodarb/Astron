package dev.kobzar.onboarding.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
internal fun OnboardingScreenDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spaces.space24, vertical = AppTheme.spaces.space4),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = AppTheme.typography.semibold26,
            color = AppTheme.colors.primary
        )
        if (description != null) {
            Text(
                text = description,
                style = AppTheme.typography.regular16,
                color = AppTheme.colors.primary.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = AppTheme.spaces.space16)
            )
        }
    }
}