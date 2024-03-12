package dev.kobzar.details.components.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.kobzar.details.R
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getDiameterRangeByUnit
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getMissDistanceUnit
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getRelativeVelocity
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.ui.compose.components.containers.OutlineBox
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
fun DetailsTable(
    modifier: Modifier = Modifier,
    data: MainDetailsModel,
    userPrefs: UserPreferencesModel?
    ) {

    val closeApproachData = data.closeApproachData[0] // The closest date to the current time

    val missDistanceUnit = closeApproachData.getMissDistanceUnit(userPrefs)
    val relativeVelocity = closeApproachData.getRelativeVelocity(userPrefs)
    val diameterUnits = data.estimatedDiameter.getDiameterRangeByUnit(userPrefs?.diameterUnits ?: DiameterUnit.KILOMETER)

    val diameterPrefixUnit = userPrefs?.diameterUnits?.unit?.let { stringResource(id = it) } ?: "N/A"
    val relativeVelocityPrefix = userPrefs?.relativeVelocityUnits?.unit?.let { stringResource(id = it) } ?: "N/A"
    val missDistancePrefix = userPrefs?.missDistanceUnits?.unit?.let { stringResource(id = it) } ?: "N/A"

    OutlineBox(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spaces.space16)
    ) {
        Column {
            DetailsTableItem(
                title = stringResource(R.string.details_table_item_dangerous),
                itemValue = data.isDangerous.toString(),
                isDangerItem = true
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_min_diameter),
                itemValue = "${diameterUnits.first} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_max_diameter),
                itemValue = "${diameterUnits.second} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_close_approach),
                itemValue = closeApproachData.closeApproachDateFull
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_relative_velocity),
                itemValue = "$relativeVelocity $relativeVelocityPrefix"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_miss_distance),
                itemValue = "$missDistanceUnit $missDistancePrefix"
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_orbiting_body),
                itemValue = closeApproachData.orbitingBody
            )

            DetailsTableItem(
                title = stringResource(R.string.details_table_item_is_sentry_object),
                itemValue = data.isSentryObject.toString(),
                isSentryItem = true
            )
        }
    }
}