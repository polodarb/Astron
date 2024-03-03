package dev.kobzar.ui.compose.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun OnBoardingSkipButton(
    onSkipClick: () -> Unit,
    skipVisible: Boolean
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        AnimatedContent(targetState = skipVisible, label = "SkipAnimation") {
            if (it) {
                Text(
                    text = "Skip",
                    style = AppTheme.typography.mediumSubtitle,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onSkipClick()
                        }
                        .padding(8.dp),
                    textAlign = TextAlign.End
                )
            } else {
                Text(
                    text = String(),
                    style = AppTheme.typography.mediumSubtitle,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}