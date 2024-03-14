package dev.kobzar.settings.dialogs

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.settings.R
import dev.kobzar.ui.compose.theme.dialogsTheme.CustomDialogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdateSyncDialog(
    showDialog: UseCaseState,
    syncValue: Int?,
    onItemSelected: (Int) -> Unit
) {

    val syncHours = listOf(1, 2, 5, 10, 24, 48)

    val options = List(syncHours.size) { index ->
        Option(
            titleText = syncHours[index].toString(),
            selected = syncHours[index] == syncValue
        )
    }

    CustomDialogTheme {
        OptionDialog(
            state = showDialog,
            selection = OptionSelection.Single(options) { index, _ ->
                onItemSelected(syncHours[index])
            },
            config = OptionConfig(mode = DisplayMode.LIST)
        )
    }
}