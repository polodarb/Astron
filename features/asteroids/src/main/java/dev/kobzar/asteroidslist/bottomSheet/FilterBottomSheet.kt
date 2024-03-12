package dev.kobzar.asteroidslist.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.kobzar.asteroidslist.R
import dev.kobzar.ui.compose.components.buttons.icon.OutlineIconButton
import dev.kobzar.ui.compose.components.buttons.main.OutlineButton
import dev.kobzar.ui.compose.components.buttons.main.PrimaryButton
import dev.kobzar.ui.compose.modifiers.animateClickable
import dev.kobzar.ui.compose.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    dangerCheckedState: Boolean,
    onDangerCheckedChange: (Boolean) -> Unit,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    firstDate: String?,
    secondDate: String?,
    onChooseDateClick: () -> Unit,
    onSaveClick: (dangerous: Boolean) -> Unit,
    onDismiss: () -> Unit
) {

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            shape = RoundedCornerShape(size = 12.dp), // TODO: Review shape
            containerColor = AppTheme.colors.background,
            dragHandle = null,
            onDismissRequest = onDismiss,
            windowInsets = WindowInsets(0, 0, 0, 0),
            sheetState = sheetState
        ) {

            // TopBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spaces.space16),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Asteroids Filter",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.semibold26
                )
                OutlineIconButton(
                    icon = {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    },
                    onClick = onDismiss
                )
            }

            // Dates
            if (firstDate != null && secondDate != null) {
                BsDateRange(
                    firstDate = firstDate,
                    secondDate = secondDate,
                    onChooseDateClick = onChooseDateClick
                )
            }

            // Other
            HorizontalDivider(
                modifier = Modifier.padding(top = AppTheme.spaces.space8),
                color = AppTheme.colors.secondaryBlue100
            )
            BsFilterItem(
                checkedState = dangerCheckedState,
                onStateChange = {
                    onDangerCheckedChange(it)
                }
            )

            PrimaryButton(
                onClick = {
                    onSaveClick(dangerCheckedState)
                },
                text = "Save",
                modifier = Modifier
                    .padding(AppTheme.spaces.space16)
                    .fillMaxWidth()
            )

            NavbarBsSpace()
        }
    }
}

@Composable
private fun BsDateRange(
    firstDate: String,
    secondDate: String,
    onChooseDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.spaces.space16)
            .fillMaxWidth(),
    ) {
        Text(
            text = "Dates",
            modifier = Modifier.padding(bottom = AppTheme.spaces.space12),
            style = AppTheme.typography.medium14,
            color = AppTheme.colors.secondaryGray600
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BsDateChip(firstDate)
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_range),
                contentDescription = null
            )
            BsDateChip(secondDate)
        }
        OutlineButton(
            onClick = onChooseDateClick,
            text = "Choose a date range",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spaces.space16)
        )
    }
}

@Composable
private fun BsFilterItem(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit
) {

    Row(
        modifier
            .fillMaxWidth()
            .height(60.dp)
            .animateClickable(
                onClick = {
                    onStateChange(!checkedState)
                }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "By dangerous",
            style = AppTheme.typography.regular16,
        )
        Checkbox(
            checked = checkedState,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.secondaryGray700
            )
        )
    }
}

@Composable
private fun BsDateChip(
    date: String
) {
    Text(
        text = date,
        style = AppTheme.typography.semibold16,
        color = AppTheme.colors.darkBlue500,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AppTheme.colors.secondaryBlue100)
            .padding(
                horizontal = AppTheme.spaces.space16,
                vertical = AppTheme.spaces.space12
            )
    )
}

@Composable
private fun NavbarBsSpace() {
    Spacer(
        Modifier
            .height(
                WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding() + 4.dp
            )
            .fillMaxWidth()
    )
}