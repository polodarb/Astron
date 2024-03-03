package dev.kobzar.ui.compose.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isHover by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHover) AppTheme.colors.darkBlue700 else AppTheme.colors.primary,
        animationSpec = tween(durationMillis = 200, easing = LinearEasing),
        label = "colorAnim"
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isHover = true
                is PressInteraction.Release -> isHover = false
                is PressInteraction.Cancel -> isHover = false
            }
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = backgroundColor
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTheme.typography.mediumTitle,
            color = AppTheme.colors.white,
            modifier = Modifier.padding(horizontal = AppTheme.spaces.space16, vertical = AppTheme.spaces.space14)
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(
        onClick = {},
        text = "Primary Button"
    )
}