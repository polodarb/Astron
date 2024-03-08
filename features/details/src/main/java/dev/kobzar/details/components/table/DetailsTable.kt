package dev.kobzar.details.components.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.kobzar.details.R
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.ui.compose.components.containers.OutlineBox
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun DetailsTable(
    modifier: Modifier = Modifier,
    data: PrefsDetailsModel,
    diameterUnit: DiameterUnit?
) {

    val diameterPrefixUnit = with(data.closeApproachData) {
        when (diameterUnit) {
            DiameterUnit.KILOMETER -> "Km"
            DiameterUnit.METER -> "Meters"
            DiameterUnit.MILE -> "Miles"
            DiameterUnit.FEET -> "Feet"
            null -> ""
        }
    }

    OutlineBox(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spaces.space16)
    ) {
        Column {
            DetailsTableItem(
                title = stringResource(R.string.details_table_item_dangerous),
                value = data.isDangerous.toString(),
                isDangerItem = true
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_min_diameter),
                value = "${data.estimatedDiameter.estimatedDiameterMin} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_max_diameter),
                value = "${data.estimatedDiameter.estimatedDiameterMax} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_close_approach),
                value = data.closeApproachData.closeApproachDateFull
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_relative_velocity),
                value = data.closeApproachData.relativeVelocity
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_miss_distance),
                value = data.closeApproachData.missDistance
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_orbiting_body),
                value = data.closeApproachData.orbitingBody
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_is_sentry_object),
                value = data.isSentryObject.toString(),
                isSentryItem = true
            )
        }
    }
}