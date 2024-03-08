package dev.kobzar.onboarding.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kobzar.ui.compose.components.buttons.main.PrimaryButton
import dev.kobzar.ui.compose.components.buttons.main.SecondaryButton
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
internal fun OnboardingScreenButtons(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonType: OnboardingButtonType = OnboardingButtonType.SECONDARY
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (buttonType) {
            OnboardingButtonType.PRIMARY -> {
                PrimaryButton(
                    onClick = onClick,
                    text = "Start",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spaces.space24)
                )
            }

            OnboardingButtonType.SECONDARY -> {
                SecondaryButton(
                    onClick = onClick,
                    text = "Next",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spaces.space24)
                )
            }
        }

    }
}

internal enum class OnboardingButtonType {
    PRIMARY,
    SECONDARY
}