package dev.kobzar.details.components.indicators

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.LightGray,
    selectedIndicatorColor: Color = Color.Black
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { pageIndex ->
            val color =
                if (pageIndex == pagerState.currentPage) selectedIndicatorColor else indicatorColor

            val animateSizeChange by animateFloatAsState(
                targetValue = if (pageIndex == pagerState.currentPage) 8f else 6f,
                animationSpec = tween(200, easing = LinearEasing), label = "",
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spaces.space4)
                    .size(animateSizeChange.dp)
                    .background(color, shape = CircleShape)
            )
        }
    }
}
