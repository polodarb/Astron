package dev.kobzar.details.components.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.kobzar.details.R
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
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

    val closeApproachData = data.closeApproachData[0]

    val missDistanceUnit = when (userPrefs?.missDistanceUnits) {
        MissDistanceUnit.LUNAR -> closeApproachData.missDistance.lunar
        MissDistanceUnit.KILOMETER -> closeApproachData.missDistance.kilometers
        MissDistanceUnit.MILE -> closeApproachData.missDistance.miles
        MissDistanceUnit.ASTRONOMICAL -> closeApproachData.missDistance.astronomical
        else -> null
    }

    val missDistancePrefix = when (userPrefs?.missDistanceUnits) {
        MissDistanceUnit.LUNAR -> stringResource(R.string.unit_lunar)
        MissDistanceUnit.KILOMETER -> stringResource(R.string.unit_km)
        MissDistanceUnit.MILE -> stringResource(R.string.unit_miles)
        MissDistanceUnit.ASTRONOMICAL -> stringResource(R.string.unit_astronomical)
        else -> ""
    }

    val relativeVelocity = when (userPrefs?.relativeVelocityUnits) {
        RelativeVelocityUnit.MILE_H -> closeApproachData.relativeVelocity.milesPerHour
        RelativeVelocityUnit.KM_S -> closeApproachData.relativeVelocity.kilometersPerSecond
        RelativeVelocityUnit.KM_H -> closeApproachData.relativeVelocity.kilometersPerHour
        else -> null
    }

    val relativeVelocityPrefix = when (userPrefs?.relativeVelocityUnits) {
        RelativeVelocityUnit.MILE_H -> stringResource(R.string.unit_speed_mph)
        RelativeVelocityUnit.KM_S -> stringResource(R.string.unit_speed_km_s)
        RelativeVelocityUnit.KM_H -> stringResource(R.string.unit_speed_km_h)
        else -> ""
    }

    val diameterUnits = when (userPrefs?.diameterUnits) {
        DiameterUnit.KILOMETER -> data.estimatedDiameter.kilometers.estimatedDiameterMin to data.estimatedDiameter.kilometers.estimatedDiameterMax
        DiameterUnit.METER -> data.estimatedDiameter.meters.estimatedDiameterMin to data.estimatedDiameter.meters.estimatedDiameterMax
        DiameterUnit.MILE -> data.estimatedDiameter.miles.estimatedDiameterMin to data.estimatedDiameter.miles.estimatedDiameterMax
        DiameterUnit.FEET -> data.estimatedDiameter.feet.estimatedDiameterMin to data.estimatedDiameter.feet.estimatedDiameterMax
        else -> null to null
    }

    val diameterPrefixUnit = with(data.closeApproachData) {
        when (userPrefs?.diameterUnits) {
            DiameterUnit.KILOMETER -> stringResource(R.string.unit_km)
            DiameterUnit.METER -> stringResource(R.string.unit_meters)
            DiameterUnit.MILE -> stringResource(R.string.unit_miles)
            DiameterUnit.FEET -> stringResource(R.string.unit_feets)
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