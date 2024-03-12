package dev.kobzar.ui.compose.components.buttons.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.modifiers.animateClickable
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .animateClickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTheme.typography.medium16,
            color = AppTheme.colors.tertiaryViolet900,
            modifier = Modifier.padding(horizontal = AppTheme.spaces.space16, vertical = AppTheme.spaces.space14)
        )
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    SecondaryButton(
        onClick = {},
        text = "Secondary Button"
    )
}