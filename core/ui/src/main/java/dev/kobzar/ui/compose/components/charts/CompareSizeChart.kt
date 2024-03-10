package dev.kobzar.ui.compose.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

@Composable
fun CompareSizeChart(
    modifier: Modifier = Modifier,
    objectDiameterInKm: Float,
    objectName: String
) {
    val bigCircleRadius = 950f / 2
    val smallCircleRadius = objectDiameterInKm / 2

    val zoomMultiplier = (bigCircleRadius / smallCircleRadius)

    val initialZoom = minOf(
        LocalDensity.current.run { (LocalConfiguration.current.screenWidthDp.dp / (bigCircleRadius * 1.3f)).value },
        LocalDensity.current.run { (LocalConfiguration.current.screenHeightDp.dp / (bigCircleRadius * 1.25f)).value }
    )

    var isAlreadyShow by rememberSaveable {
        mutableStateOf(false)
    }

    var zoomState by rememberSaveable { mutableFloatStateOf(zoomMultiplier) }
    var gridZoomState by rememberSaveable { mutableFloatStateOf(zoomMultiplier) }
    var generalZoom by rememberSaveable { mutableFloatStateOf(1f) }

    val numIterations = 200
    val stepZoomState = (initialZoom.toDouble() / zoomMultiplier).pow(1.0 / numIterations)
    val stepGridZoomState = (1 / zoomMultiplier).pow(1.0f / numIterations)

    val coroutineScope = rememberCoroutineScope()

    if (!isAlreadyShow) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(700)
                for (i in 0..numIterations) {
                    zoomState = zoomMultiplier * stepZoomState.pow(i.toDouble()).toFloat()
                    delay(8)
                }
            }
            coroutineScope.launch {
                delay(700)
                for (i in 1..numIterations) {
                    gridZoomState = zoomMultiplier * stepGridZoomState.pow(i)
                    delay(8)
                }
            }
            isAlreadyShow = true
        }
    } else {
        zoomState = initialZoom
        gridZoomState = zoomMultiplier
        generalZoom = 1f
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF))
            .border(1.dp, Color(0xFF8490B2), RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(0.9f)) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, gestureZoom, _ ->
                            val newZoom = zoomState * gestureZoom
                            val newGridZoom = gridZoomState * gestureZoom
                            if (newGridZoom in 1.0..zoomMultiplier.toDouble()) {
                                zoomState = newZoom
                                generalZoom *= gestureZoom
                                gridZoomState = newGridZoom
                            }

                        }
                    }
                    .padding(start = 16.dp, end = 16.dp)

            ) {
                val focusCenter = Offset(size.width * 3 / 4, size.height - smallCircleRadius)
                val zoomFocusCenter = Offset(size.width * 3 / 4 + smallCircleRadius, size.height)

                val rowHeight = size.height / 6 * gridZoomState
                val firstLineY = size.height

                repeat(6) {
                    drawLine(
                        color = Color(0xFFCDDAFB),
                        start = Offset(0f, firstLineY - it * rowHeight),
                        end = Offset(size.width, firstLineY - it * rowHeight),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                scale(zoomState, zoomFocusCenter) {
                    drawCircle(
                        color = Color(0xFFCDDAFB),
                        radius = bigCircleRadius,
                        center = Offset(size.width / 4 - bigCircleRadius / 4, size.height - bigCircleRadius)
                    )

                    drawCircle(
                        color = Color(0xFFBBACF9),
                        radius = smallCircleRadius,
                        center = focusCenter
                    )
                }
            }

            Text(text = "Zoom ${((generalZoom).toInt().toString())}x", modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 14.dp, horizontal = 16.dp),
                color = Color(0xFF8490B2)
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ChartItem(title = "Ceres", color = Color(0xFFCDDAFB))
            ChartItem(title = objectName, color = Color(0xFFBBACF9))
        }
    }
}

@Composable
private fun ChartItem(
    title: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color = color)
        )
        Text(text = title)
    }
}