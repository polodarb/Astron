package dev.kobzar.onboarding.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.kobzar.onboarding.R
import dev.kobzar.onboarding.screens.shared.OnboardingScreenButtons
import dev.kobzar.onboarding.screens.shared.OnboardingScreenDescription
import dev.kobzar.onboarding.screens.shared.OnboardingScreenImage

@Composable
internal fun WelcomeScreen(
    parallaxEffect: Float = 0f,
    onNextClick: () -> Unit
) {
    Column {
        OnboardingScreenImage(
            image = R.drawable.onboarding_img_1,
            parallaxOffset = parallaxEffect
        )
        OnboardingScreenDescription(
            title = stringResource(R.string.onboarding_page1_title),
            description = stringResource(R.string.onboarding_page1_desc)
        )
        OnboardingScreenButtons(onClick = onNextClick)
    }
}
