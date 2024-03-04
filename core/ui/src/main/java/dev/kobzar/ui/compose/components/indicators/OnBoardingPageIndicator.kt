package dev.kobzar.ui.compose.components.indicators

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun OnBoardingPageIndicator(currentPage: Int) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        repeat(3) { index ->

            val alpha by animateFloatAsState(
                targetValue = if (index <= currentPage) 1f else 0.25f,
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                label = "AlphaAnimation"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .weight(1f)
                    .clip(CircleShape)
                    .background(color = AppTheme.colors.primary.copy(alpha = alpha))
            )
        }
    }
}