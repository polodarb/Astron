package dev.kobzar.ui.compose.components.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestNotificationPermissionDialog(
    showDialog: UseCaseState,
    closeSelection: () -> Unit = { },
    onRequestPermission: () -> Unit
) {
    InfoDialog(
        state = showDialog,
        header = Header.Default(
            title = "Notification Permission"
        ),
        body = InfoBody.Default(
            bodyText = "We need permission to show notifications",
        ),
        selection = InfoSelection(
            onPositiveClick = onRequestPermission,
            onNegativeClick = closeSelection
        )
    )
}