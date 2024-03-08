package dev.kobzar.details.components.header

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.kobzar.details.R
import dev.kobzar.ui.compose.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsMainContentHeader(
    modifier: Modifier = Modifier,
    name: String,
    id: String,
    sbdAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = AppTheme.spaces.space36),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = AppTheme.typography.semibold24
        )
        Text(
            text = "ID: $id",
            style = AppTheme.typography.regular14,
            color = AppTheme.colors.secondaryGray600,
            modifier = Modifier.padding(top = AppTheme.spaces.space10)
        )
        SbdChip(onClick = sbdAction, modifier = Modifier.padding(top = AppTheme.spaces.space32))
    }
}

@Composable
private fun SbdChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isHover by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHover) AppTheme.colors.secondaryGray200 else AppTheme.colors.background,
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

    Row(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, AppTheme.colors.secondaryGray700, CircleShape)
            .background(
                color = backgroundColor
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .padding(AppTheme.spaces.space4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nasa),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.spaces.space4))
        Text(
            text = stringResource(R.string.details_chip_sbd),
            style = AppTheme.typography.semibold14,
            modifier = Modifier.padding(
                start = AppTheme.spaces.space4,
                end = AppTheme.spaces.space10
            )
        )
    }
}
