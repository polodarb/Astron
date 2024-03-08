package dev.kobzar.ui.compose.components.buttons.icon

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OutlineIconButton(
    modifier: Modifier = Modifier,
    icon: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    var isHover by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHover) AppTheme.colors.secondaryGray100 else AppTheme.colors.background,
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
            .size(40.dp)
            .clip(CircleShape)
            .border(1.dp, AppTheme.colors.secondaryGray200, CircleShape)
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
        content = icon
    )
}
