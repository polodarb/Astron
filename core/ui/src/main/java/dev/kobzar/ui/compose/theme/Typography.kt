package dev.kobzar.ui.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.kobzar.ui.R

data class Typography(
    val title: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    val subtitle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val mediumTitle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    val mediumSubtitle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    val subBody: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val littleInfoText: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val other: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

val LocalTypography = staticCompositionLocalOf { Typography() }
