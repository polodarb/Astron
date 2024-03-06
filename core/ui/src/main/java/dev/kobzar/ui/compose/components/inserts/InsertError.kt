package dev.kobzar.ui.compose.components.inserts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.kobzar.ui.R
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun InsertError(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.spaces.space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.pic_error), contentDescription = null)
        Text(
            text = "Something went wrong",
            style = AppTheme.typography.medium18,
            modifier = Modifier.padding(top = AppTheme.spaces.space12)
        )
        if (onRetryClick != null) {
            Button(
                onClick = onRetryClick, colors = ButtonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.white,
                    disabledContainerColor = AppTheme.colors.secondaryGray100,
                    disabledContentColor = AppTheme.colors.secondaryGray800
                ),
                shape = ShapeDefaults.Medium,
                modifier = Modifier
                    .padding(top = AppTheme.spaces.space12)
            ) {
                Text(text = "Retry", style = AppTheme.typography.regular14)
            }
        }
    }
}