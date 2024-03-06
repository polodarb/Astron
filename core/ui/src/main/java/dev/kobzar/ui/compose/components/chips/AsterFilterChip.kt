package dev.kobzar.ui.compose.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun AsterFilterChip(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val borderColor = if (selected) AppTheme.colors.primary else AppTheme.colors.outline
    val backgroundColor = if (selected) AppTheme.colors.primary else Color.Transparent
    val textColor = if (selected) AppTheme.colors.white else AppTheme.colors.outline

    Box(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .border(1.dp, borderColor, RoundedCornerShape(8.dp))
        .background(backgroundColor)
        .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = textColor,
            style = AppTheme.typography.medium14,
            modifier = Modifier
                .padding(vertical = AppTheme.spaces.space8, horizontal = AppTheme.spaces.space16)
                .align(Alignment.Center)
        )
    }
}