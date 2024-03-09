package dev.kobzar.details.components.compare

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.kobzar.details.R
import dev.kobzar.details.components.compare.charts.distance.DetailsCompareDistanceChart
import dev.kobzar.ui.compose.components.charts.CompareSizeChart
import dev.kobzar.ui.compose.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsCompareScreen(
    modifier: Modifier = Modifier,
    objectName: String,
    objectSize: Float,
    astronomicalDistance: Float,
    comparePagerState: PagerState,
    switchIndexPosition: Int,
    onSwitchIndexPosition: (Int) -> Unit
) {
    Column(modifier = Modifier) {
        HorizontalPager(
            state = comparePagerState,
            userScrollEnabled = false,
            modifier = Modifier.animateContentSize(
                animationSpec = tween(200, easing = FastOutSlowInEasing)
            ),
            verticalAlignment = Alignment.Top
        ) {
            when (it) {
                0 -> CompareSizeChart(
                    objectDiameterInKm = objectSize,
                    objectName = objectName,
                    modifier = modifier
                )

                1 -> DetailsCompareDistanceChart(
                    astronomicalDistance = astronomicalDistance,
                    modifier = modifier
                )
            }
        }
        DetailsCompareSwitcher(
            selectedIndex = switchIndexPosition,
            onIndexChange = onSwitchIndexPosition
        )
    }
}

@Composable
fun DetailsCompareSwitcher(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onIndexChange: (Int) -> Unit
) {

    val options = listOf(
        stringResource(R.string.details_switch_title_size),
        stringResource(R.string.details_switch_title_distance)
    )

    Box(
        modifier = modifier
            .padding(
                start = AppTheme.spaces.space48,
                end = AppTheme.spaces.space48,
                top = AppTheme.spaces.space16
            )
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF8490B2), RoundedCornerShape(12.dp))
            .background(Color.White)
            .height(52.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .background(
                            if (selectedIndex == index) {
                                AppTheme.colors.secondaryBlue100
                            } else {
                                Color.Transparent
                            }
                        )
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { onIndexChange(index) }
                        .padding(
                            vertical = AppTheme.spaces.space16,
                            horizontal = AppTheme.spaces.space24
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(option, style = AppTheme.typography.medium14)
                }
            }
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = AppTheme.spaces.space6)
        )
    }
}
