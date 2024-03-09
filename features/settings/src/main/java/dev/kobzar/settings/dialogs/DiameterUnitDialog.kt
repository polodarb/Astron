package dev.kobzar.settings.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.settings.R
import dev.kobzar.ui.compose.theme.dialogsTheme.CustomDialogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DiameterUnitDialog(
    showDialog: UseCaseState,
    diameterPrefs: DiameterUnit?,
    onItemSelected: (DiameterUnit) -> Unit
) {

    val diameterUnitsList = listOf(
        stringResource(id = R.string.settings_unit_km),
        stringResource(id = R.string.settings_unit_meters),
        stringResource(id = R.string.settings_unit_miles),
        stringResource(id = R.string.settings_unit_feets)
    )

    val selectedDiameterItemByPrefs = when (diameterPrefs) {
        DiameterUnit.KILOMETER -> 0
        DiameterUnit.METER -> 1
        DiameterUnit.MILE -> 2
        DiameterUnit.FEET -> 3
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
        ),
        Option(
            titleText = diameterUnitsList[3],
            selected = selectedDiameterItemByPrefs == 3
        ),
    )

    CustomDialogTheme {
        OptionDialog(
            state = showDialog,
            selection = OptionSelection.Single(options) { index, _ ->
                onItemSelected(
                    when (index) {
                        0 -> DiameterUnit.KILOMETER
                        1 -> DiameterUnit.METER
                        2 -> DiameterUnit.MILE
                        else -> DiameterUnit.FEET
                    }
                )
            },
            config = OptionConfig(mode = com.maxkeppeler.sheets.option.models.DisplayMode.LIST)
        )
    }
}