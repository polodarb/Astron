package dev.kobzar.ui.compose.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.R
import dev.kobzar.ui.compose.components.buttons.icon.OutlineIconButton
import dev.kobzar.ui.compose.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryTopBar(
    modifier: Modifier = Modifier,
    title: String,
    customAction: (@Composable (onBackClick: () -> Unit) -> Unit)? = null,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: (value: Boolean) -> Unit
) {

    val haptic = LocalHapticFeedback.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = AppTheme.colors.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = AppTheme.typography.semibold16
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background
        ),
        modifier = modifier.padding(horizontal = AppTheme.spaces.space12),
        navigationIcon = {
            OutlineIconButton(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                        modifier = Modifier.size(26.dp)
                    )
                },
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onBackClick()
                }
            )
        },
        actions = {
            if (customAction != null) {
                customAction(onBackClick)
            } else {
                OutlineIconButton(
                    icon = {
                        Icon(
                            painter = if (isFavorite) {
                                painterResource(id = R.drawable.ic_bookmark)
                            } else {
                                painterResource(id = R.drawable.ic_bookmark_fill)
                            },
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onFavoriteClick(!isFavorite)
                    }
                )
            }
        }
    )
}