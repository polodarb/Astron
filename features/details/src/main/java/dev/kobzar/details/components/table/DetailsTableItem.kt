package dev.kobzar.details.components.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.kobzar.details.R
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun DetailsTableItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isDangerItem: Boolean = false,
    isSentryItem: Boolean = false
) {

    val dangerTextColor = if (isDangerItem) {
        val result = value.toBoolean()
        if (result) AppTheme.colors.secondaryRed else AppTheme.colors.secondaryGreen
    } else {
        AppTheme.colors.primary
    }

    val dangerOrSentryText = if (isDangerItem || isSentryItem) {
        val result = value.toBoolean()
        if (result) stringResource(R.string.yes) else stringResource(R.string.no)
    } else {
        value
    }

    val isDangerOrSentry = isDangerItem || isSentryItem

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = AppTheme.typography.medium16
            )
            Text(
                text = if (isDangerOrSentry) dangerOrSentryText else value,
                style = AppTheme.typography.semibold14,
                color = if (isDangerItem) dangerTextColor else AppTheme.colors.primary
            )
        }
    }
    HorizontalDivider(color = AppTheme.colors.secondaryBlue100)
}