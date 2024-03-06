package dev.kobzar.ui.compose.components.inserts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import dev.kobzar.ui.R
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun InsertNoData(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.spaces.space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.pic_area), contentDescription = null)
        Text(
            text = "No data!",
            style = AppTheme.typography.medium22,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = AppTheme.spaces.space20)
        )
        Text(
            text = "Check your network connection",
            style = AppTheme.typography.medium16,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = AppTheme.spaces.space12)
        )
    }
}