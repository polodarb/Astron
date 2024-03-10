package dev.kobzar.ui.compose.components.fabs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun PrimaryFAB(
    modifier: Modifier = Modifier,
    title: String,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(6.dp),
    @DrawableRes iconRes: Int,
    onFabClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onFabClick,
        text = {
            Text(text = title, style = AppTheme.typography.regular16)
        },
        icon = {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        },
        elevation = elevation,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.white,
        modifier = modifier.height(60.dp)
    )
}