package dev.kobzar.onboarding.screens.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
internal fun OnboardingScreenImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    heightFraction: Float = 0.6f,
    scaleType: ContentScale = ContentScale.Crop,
    parallaxOffset: Float = 0f
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(heightFraction),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentScale = scaleType,
            modifier = Modifier
                .fillMaxWidth()
                .offset(
                    x = if (parallaxOffset > 0) (parallaxOffset * 300).dp else -(parallaxOffset * 300).dp,
                    y = 0.dp
                )
                .padding(horizontal = AppTheme.spaces.space24),
            contentDescription = null
        )
    }
}
