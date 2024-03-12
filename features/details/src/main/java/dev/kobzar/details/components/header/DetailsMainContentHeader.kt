package dev.kobzar.details.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.kobzar.details.R
import dev.kobzar.ui.compose.modifiers.animateClickable
import dev.kobzar.ui.compose.theme.AppTheme

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

    Row(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, AppTheme.colors.secondaryGray700, CircleShape)
            .animateClickable(
                onClick = onClick,
                defaultColor = AppTheme.colors.background,
                pressedColor = AppTheme.colors.secondaryGray200
            )
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
