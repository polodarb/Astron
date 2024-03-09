package dev.kobzar.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.containers.OutlineColumn
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme

class SettingsScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<SettingsViewModel>()

        val navigator = LocalNavigator.current

        val prefs = viewModel.userPrefs.collectAsState()

        SettingsStatesScreenComposable(
            prefs = prefs.value,
            onUpdateDiameterUnit = {
                viewModel.updateDiameterUnit(it)
            },
            onUpdateMissDistanceUnit = {
                viewModel.updateMissDistanceUnit(it)
            },
            onUpdateRelativeVelocityUnit = {
                viewModel.updateRelativeVelocityUnit(it)
            },
            onBackClick = { navigator?.pop() }
        )

    }

}

@Composable
private fun SettingsStatesScreenComposable(
    prefs: UiState<UserPreferencesModel>,
    onUpdateDiameterUnit: (DiameterUnit) -> Unit,
    onUpdateMissDistanceUnit: (MissDistanceUnit) -> Unit,
    onUpdateRelativeVelocityUnit: (RelativeVelocityUnit) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(title = "Settings", onBackClick = onBackClick)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
        ) {
            when (prefs) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppTheme.colors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        InsertLoader(text = "Loading preferences")
                    }
                }

                is UiState.Error -> InsertError()

                is UiState.Success -> {
                    SettingsScreenComposable(
                        data = prefs.data,
                        modifier = Modifier.padding(top = AppTheme.spaces.space12)
                    ) // todo
                }
            }
        }
    }
}

@Composable
private fun SettingsScreenComposable(
    modifier: Modifier = Modifier,
    data: UserPreferencesModel
) {

    val diameterUnits = when (data.diameterUnits) {
        DiameterUnit.KILOMETER -> "Kilometers"
        DiameterUnit.METER -> "Meters"
        DiameterUnit.MILE -> "Miles"
        DiameterUnit.FEET -> "Feet"
    }

    val missDistanceUnits = when (data.missDistanceUnits) {
        MissDistanceUnit.KILOMETER -> "Kilometers"
        MissDistanceUnit.LUNAR -> "Lunar"
        MissDistanceUnit.MILE -> "Miles"
        MissDistanceUnit.ASTRONOMICAL -> "Astronomical"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.spaces.space16)
    ) {
        Text(
            text = "General",
            style = AppTheme.typography.medium14,
            modifier = Modifier.padding(
                bottom = AppTheme.spaces.space8
            ),
            color = AppTheme.colors.secondaryGray700
        )
        OutlineColumn {
            SettingsItem(title = "Diameter unit") {
                Text(
                    text = diameterUnits,
                    color = AppTheme.colors.secondaryGray800,
                    modifier = Modifier.settingsItemValue {

                    }
                )
            }

            SettingsItem(title = "Miss distance unit") {
                Text(text = missDistanceUnits, modifier = Modifier.settingsItemValue {

                })
            }

            SettingsItem(title = "Relative velocity unit") {
                Text(text = "${data.relativeVelocityUnits.name}", modifier = Modifier.settingsItemValue {

                })
            }
        }

        Text(
            text = "Other",
            style = AppTheme.typography.medium14,
            modifier = Modifier.padding(
                bottom = AppTheme.spaces.space8,
                top = AppTheme.spaces.space32
            ),
            color = AppTheme.colors.secondaryGray700
        )
        OutlineColumn {
            SettingsItem(title = "Danger notifications") {
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        uncheckedThumbColor = AppTheme.colors.secondaryGray600,
                        uncheckedBorderColor = AppTheme.colors.secondaryGray600,
                        uncheckedTrackColor = AppTheme.colors.secondaryGray100,
                        checkedTrackColor = AppTheme.colors.primary
                    )
                )
            }
        }
    }

}

@Composable
fun Modifier.settingsItemValue(
    onClick: () -> Unit
): Modifier {
    return this
        .clip(RoundedCornerShape(6.dp))
        .background(AppTheme.colors.secondaryGray100)
        .clickable {
            onClick()
        }
        .padding(vertical = 6.dp, horizontal = 8.dp)
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    itemAction: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = AppTheme.typography.medium16
            )
            Box(content = itemAction)
        }
    }
    HorizontalDivider(color = AppTheme.colors.secondaryBlue100)
}