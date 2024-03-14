package dev.kobzar.settings.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.settings.R
import dev.kobzar.ui.compose.theme.dialogsTheme.CustomDialogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RelativeVelocityUnitDialog(
    showDialog: UseCaseState,
    relativeVelocity: RelativeVelocityUnit?,
    onItemSelected: (RelativeVelocityUnit) -> Unit
) {

    val diameterUnitsList = listOf(
        stringResource(id = R.string.settings_unit_speed_km_h),
        stringResource(id = R.string.settings_unit_speed_km_s),
        stringResource(id = R.string.settings_unit_speed_mph)
    )

    val selectedDiameterItemByPrefs = when (relativeVelocity) {
        RelativeVelocityUnit.KM_H -> 0
        RelativeVelocityUnit.KM_S -> 1
        RelativeVelocityUnit.MILE_H -> 2
        else -> null
    }

    val options = listOf(
        Option(
            titleText = diameterUnitsList[0],
            selected = selectedDiameterItemByPrefs == 0
        ),
        Option(
            titleText = diameterUnitsList[1],
            selected = selectedDiameterItemByPrefs == 1
        ),
        Option(
            titleText = diameterUnitsList[2],
            selected = selectedDiameterItemByPrefs == 2
        )
    )

    CustomDialogTheme {
        OptionDialog(
            state = showDialog,
            selection = OptionSelection.Single(options) { index, _ ->
                onItemSelected(
                    when (index) {
                        0 -> RelativeVelocityUnit.KM_H
                        1 -> RelativeVelocityUnit.KM_S
                        else -> RelativeVelocityUnit.MILE_H
                    }
                )
            },
            config = OptionConfig(mode = com.maxkeppeler.sheets.option.models.DisplayMode.LIST)
        )
    }
}