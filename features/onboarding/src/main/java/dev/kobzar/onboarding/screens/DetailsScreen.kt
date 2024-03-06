package dev.kobzar.onboarding.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.kobzar.onboarding.R
import dev.kobzar.onboarding.screens.shared.OnboardingScreenButtons
import dev.kobzar.onboarding.screens.shared.OnboardingScreenDescription
import dev.kobzar.onboarding.screens.shared.OnboardingScreenImage

@Composable
internal fun DetailsScreen(
    onNextClick: () -> Unit
) {
    Column {
        OnboardingScreenImage(
            image = R.drawable.onboarding_img_2
        )
        OnboardingScreenDescription(
            title = stringResource(R.string.onboarding_page2_title),
            description = stringResource(R.string.onboarding_page2_desc)
        )
        OnboardingScreenButtons(onClick = onNextClick)
    }
}