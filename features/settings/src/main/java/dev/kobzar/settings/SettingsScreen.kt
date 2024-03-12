package dev.kobzar.settings

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.settings.dialogs.DiameterUnitDialog
import dev.kobzar.settings.dialogs.MissDistanceUnitDialog
import dev.kobzar.settings.dialogs.RelativeVelocityUnitDialog
import dev.kobzar.ui.compose.components.containers.OutlineColumn
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme
import dev.shreyaspatil.permissionFlow.utils.launch
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import dev.shreyaspatil.permissionflow.compose.rememberPermissionState

class SettingsScreen : Screen {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun Content() {

        val viewModel = getScreenModel<SettingsViewModel>()

        val navigator = LocalNavigator.current

        val prefs = viewModel.userPrefs.collectAsState()

        SettingsStatesScreenComposable(
            prefs = prefs.value,
            onUpdatePrefsData = {
                viewModel.updatePrefsData(it)
            },
            onBackClick = { navigator?.pop() }
        )

    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun SettingsStatesScreenComposable(
    prefs: UiState<UserPreferencesModel?>,
    onUpdatePrefsData: (UserPreferencesModel) -> Unit,
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

                is UiState.Error -> {}

                is UiState.Success -> {
                    SettingsScreenComposable(
                        data = prefs.data,
                        modifier = Modifier.padding(top = AppTheme.spaces.space12),
                        onPrefsUpdate = onUpdatePrefsData
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun SettingsScreenComposable(
    modifier: Modifier = Modifier,
    data: UserPreferencesModel?,
    onPrefsUpdate: (UserPreferencesModel) -> Unit
) {

    val context = LocalContext.current

    val permissionLauncher = rememberPermissionFlowRequestLauncher()
    val state by rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    val diameterDialogShow = rememberUseCaseState(embedded = false)
    val missDistanceDialogShow = rememberUseCaseState(embedded = false)
    val relativeVelocityDialogShow = rememberUseCaseState(embedded = false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.spaces.space16)
    ) {
        Text(
            text = stringResource(R.string.settings_title_general),
            style = AppTheme.typography.medium14,
            modifier = Modifier.padding(
                bottom = AppTheme.spaces.space8
            ),
            color = AppTheme.colors.secondaryGray700
        )
        OutlineColumn {
            SettingsItem(title = "Diameter unit") {
                Text(text = data?.diameterUnits?.unit?.let { stringResource(id = it) } ?: "N/A",
                color = AppTheme.colors.secondaryGray800,
                    modifier = Modifier.settingsItemValue {
                        diameterDialogShow.show()
                    }
                )
            }

            SettingsItem(title = "Miss distance unit") {
                Text(text = data?.missDistanceUnits?.unit?.let { stringResource(id = it) } ?: "N/A",
                    color = AppTheme.colors.secondaryGray800,
                    modifier = Modifier.settingsItemValue {
                        missDistanceDialogShow.show()
                    })
            }

            SettingsItem(title = "Relative velocity unit") {
                Text(text = data?.relativeVelocityUnits?.unit?.let { stringResource(id = it) } ?: "N/A",
                    color = AppTheme.colors.secondaryGray800,
                    modifier = Modifier.settingsItemValue {
                        relativeVelocityDialogShow.show()
                    })
            }
        }

        Text(
            text = stringResource(R.string.settings_title_other),
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
                    checked = state.isGranted, onCheckedChange = {
                        if (it) {
                            Log.e("TAG", "SettingsScreenComposable: ${state}")
                            if (!state.isGranted && state.isRationaleRequired == false) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                context.startActivity(intent)
                            }
                        } else {
                            Toast.makeText(context, "You can disable notifications in settings", Toast.LENGTH_SHORT).show()
                        }
                    }, colors = SwitchDefaults.colors(
                        uncheckedThumbColor = AppTheme.colors.secondaryGray600,
                        uncheckedBorderColor = AppTheme.colors.secondaryGray600,
                        uncheckedTrackColor = AppTheme.colors.secondaryGray100,
                        checkedTrackColor = AppTheme.colors.primary
                    )
                )
            }
        }

        DiameterUnitDialog(
            showDialog = diameterDialogShow,
            diameterPrefs = data?.diameterUnits,
            onItemSelected = {
                if (data != null) {
                    onPrefsUpdate(
                        UserPreferencesModel(
                            diameterUnits = it,
                            relativeVelocityUnits = data.relativeVelocityUnits,
                            missDistanceUnits = data.missDistanceUnits
                        )
                    )
                }
            }
        )

        MissDistanceUnitDialog(
            showDialog = missDistanceDialogShow,
            missDistanceUnit = data?.missDistanceUnits,
            onItemSelected = {
                if (data != null) {
                    onPrefsUpdate(
                        UserPreferencesModel(
                            diameterUnits = data.diameterUnits,
                            relativeVelocityUnits = data.relativeVelocityUnits,
                            missDistanceUnits = it
                        )
                    )
                }
            }
        )

        RelativeVelocityUnitDialog(
            showDialog = relativeVelocityDialogShow,
            relativeVelocity = data?.relativeVelocityUnits,
            onItemSelected = {
                if (data != null) {
                    onPrefsUpdate(
                        UserPreferencesModel(
                            diameterUnits = data.diameterUnits,
                            relativeVelocityUnits = it,
                            missDistanceUnits = data.missDistanceUnits
                        )
                    )
                }
            }
        )
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