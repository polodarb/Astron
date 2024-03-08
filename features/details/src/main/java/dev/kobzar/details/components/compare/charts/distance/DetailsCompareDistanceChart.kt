package dev.kobzar.details.components.compare.charts.distance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.kobzar.details.R
import dev.kobzar.details.components.table.DetailsTableItem
import dev.kobzar.ui.compose.components.charts.CompareDistanceChart
import dev.kobzar.ui.compose.components.containers.OutlineBox
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun DetailsCompareDistanceChart(
    astronomicalDistance: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CompareDistanceChart(
            astronomicalDistance = astronomicalDistance
        )
        OutlineBox(
            modifier = Modifier.padding(top = AppTheme.spaces.space16)
        ) {
            DetailsTableItem(
                title = stringResource(R.string.details_chart_title_astronomical_distance),
                value = astronomicalDistance.toString(),
                modifier = Modifier.padding(vertical = AppTheme.spaces.space8)
            )
        }
    }
}