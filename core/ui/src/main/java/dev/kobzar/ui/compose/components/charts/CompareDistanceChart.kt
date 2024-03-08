package dev.kobzar.ui.compose.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.components.containers.OutlineBox

@Composable
fun CompareDistanceChart(
    modifier: Modifier = Modifier,
    astronomicalDistance: Float
) {
    OutlineBox(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val rowHeight = size.height / 4
            val firstLineY = size.height - rowHeight

            repeat(3) {
                drawLine(
                    color = Color(0xFFCDDAFB),
                    start = Offset(0f, firstLineY - it * rowHeight),
                    end = Offset(size.width, firstLineY - it * rowHeight),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val canvasWidth = size.width

            val sunSize = 296f
            val earthSize = 64f
            val asteroidSize = 24f

            val distanceEarthSun = canvasWidth / 3f

            val sunX = canvasWidth / 0.9f
            val sunXLimit = sunX - sunSize

            val earthX = sunXLimit - distanceEarthSun

            val earthXLimit = earthX - earthSize

            val astronomicalValue = sunXLimit - earthXLimit

            val asteroidX =
                earthXLimit - asteroidSize - (astronomicalDistance * astronomicalValue) + (astronomicalDistance * earthSize * 2f)

            val earthBrush = Brush.radialGradient(
                listOf( Color(0xFF6CDE93), Color(0xFF198BCB)),
                tileMode = TileMode.Clamp,
                radius = 124f,
                center = Offset(earthX - 48, earthX / 2f)
            )

            val sunBrush = Brush.radialGradient(
                listOf( Color(0xFFFFBB29), Color(0xFFE47100)),
                tileMode = TileMode.Clamp,
                radius = sunSize,
                center = Offset(sunX - sunSize / 2, sunX / 8)
            )

            val asteroidBrush = Brush.radialGradient(
                listOf( Color(0xFFAAA9A9), Color(0xFF564B4B)),
                tileMode = TileMode.Clamp,
                radius = asteroidSize * 3,
                center = Offset(asteroidX - asteroidSize * 1.5f, asteroidX)
            )

            scale(
                when {
                    astronomicalDistance >= 1f -> 0.8f
                    astronomicalDistance <= 0.1f -> 1.2f
                    else -> 1f
                }
            ) {
                drawCircle(
                    brush = earthBrush,
                    radius = earthSize,
                    center = Offset(earthX, size.height / 2f)
                )

                drawCircle(
                    brush = sunBrush,
                    radius = sunSize,
                    center = Offset(sunX, size.height / 2f)
                )

                drawCircle(
                    brush = asteroidBrush,
                    radius = asteroidSize,
                    center = Offset(asteroidX, size.height / 2f)
                )
            }

        }
    }
}